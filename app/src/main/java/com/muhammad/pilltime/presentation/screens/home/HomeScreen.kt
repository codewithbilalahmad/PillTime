package com.muhammad.pilltime.presentation.screens.home

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.pilltime.R
import com.muhammad.pilltime.presentation.components.AppAlertDialog
import com.muhammad.pilltime.presentation.components.BottomNavigationBar
import com.muhammad.pilltime.presentation.navigation.Destinations
import com.muhammad.pilltime.presentation.screens.home.components.HomeHeader
import com.muhammad.pilltime.presentation.screens.home.components.medicationDataSection
import com.muhammad.pilltime.utils.ObserveAsEvents
import com.muhammad.pilltime.utils.openAlarmSettings
import com.muhammad.pilltime.utils.openAppSettings
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(navHostController: NavHostController, viewModel: HomeViewModel) {
    val context = LocalContext.current
    val activity = context as Activity
    val layoutDirection = LocalLayoutDirection.current
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val scrollBehavior = FloatingToolbarDefaults.exitAlwaysScrollBehavior(exitDirection = FloatingToolbarExitDirection.Bottom)
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isNotificationPermissionPermenentlyDenied =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_DENIED && !ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            false
        }
    ObserveAsEvents(viewModel.events, onEvent = { event ->
        when (event) {
            is HomeEvent.ScrollToMedicine -> {
                val index = state.medications.indexOfFirst { it.id == event.medicineId }
                if (index != -1) {
                    scope.launch {
                        listState.animateScrollToItem(index + 1)
                    }
                }
            }
        }
    })
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { }
    LaunchedEffect(Unit) {
        delay(3000L)
        viewModel.onAction(HomeAction.OnCheckReminderPermissions)
    }
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior)) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(
                    start = paddingValues.calculateLeftPadding(layoutDirection = layoutDirection),
                    end = paddingValues.calculateEndPadding(layoutDirection = layoutDirection),
                    bottom = paddingValues.calculateBottomPadding(),
                ), state = listState, verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item("HomeHeader") {
                    HomeHeader(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(), username = state.username, onAddMedicationScreen = {
                            navHostController.navigate(Destinations.AddMedicationScreen)
                        }
                    )
                }
                medicationDataSection(
                    medications = state.medications,
                    onDateSelected = { date ->
                        viewModel.onAction(HomeAction.OnFilterDataSelected(date))
                    }, onToggleMedicineSchedules = { medicineId ->
                        viewModel.onAction(HomeAction.OnToggleMedicineSchedules(medicineId))
                    }, onDeleteMedicine = { medicineId ->
                        viewModel.onAction(HomeAction.OnDeleteMedicineById(medicineId))
                    }, isMedicinesLoading = state.isMedicinesLoading,
                    selectedDate = state.selectedFilterDate
                )
            }
            BottomNavigationBar(
                navController = navHostController,
                scrollBehavior = scrollBehavior,
                modifier = Modifier.align(
                    Alignment.BottomCenter
                )
            )
        }
    }
    if (state.showAllowNotificationAccessDialog) {
        AppAlertDialog(
            onDismiss = {
                viewModel.onAction(HomeAction.OnToggleAllowNotificationAccessDialog)
            },
            titleContent = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_notification),
                        contentDescription = null, modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = stringResource(R.string.allow_notifications),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            },
            message = stringResource(R.string.allow_notification_desp),
            confirmText = stringResource(R.string.allow),
            dismissText = stringResource(R.string.denied), onConfirmClick = {
                viewModel.onAction(HomeAction.OnToggleAllowNotificationAccessDialog)
                if (!isNotificationPermissionPermenentlyDenied) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                } else {
                    openAppSettings(context)
                }
            }, onDismissClick = {
                viewModel.onAction(HomeAction.OnToggleAllowNotificationAccessDialog)
            }
        )
    }
    if (state.showRemindersAccessDialog) {
        AppAlertDialog(
            onDismiss = {
                viewModel.onAction(HomeAction.OnToggleAllowRemindersAccessDialog)
            },
            titleContent = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_alarm),
                        contentDescription = null, modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = stringResource(R.string.allow_reminders),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            },
            message = stringResource(R.string.allow_reminder_desp),
            confirmText = stringResource(R.string.allow),
            dismissText = stringResource(R.string.denied), onConfirmClick = {
                viewModel.onAction(HomeAction.OnToggleAllowRemindersAccessDialog)
                openAlarmSettings(context)
            }, onDismissClick = {
                viewModel.onAction(HomeAction.OnToggleAllowRemindersAccessDialog)
            }
        )
    }
    if (state.showAllowNotificationAndRemindersAccessDialog) {
        AppAlertDialog(
            onDismiss = {
                viewModel.onAction(HomeAction.OnToggleAllowNotificationAndRemindersAccessDialog)
            },
            titleContent = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_notification),
                            contentDescription = null, modifier = Modifier.size(30.dp)
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_alarm),
                            contentDescription = null, modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(
                        text = stringResource(R.string.allow_notifications_and_reminders),
                        modifier = Modifier.padding(horizontal = 24.dp),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            },
            message = stringResource(R.string.allow_notification_and_reminder_desp),
            confirmText = stringResource(R.string.allow),
            dismissText = stringResource(R.string.denied), onConfirmClick = {
                viewModel.onAction(HomeAction.OnToggleAllowNotificationAndRemindersAccessDialog)
                openAppSettings(context)
            }, onDismissClick = {
                viewModel.onAction(HomeAction.OnToggleAllowNotificationAndRemindersAccessDialog)
            }
        )
    }
}