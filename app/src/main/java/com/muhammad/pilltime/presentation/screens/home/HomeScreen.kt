package com.muhammad.pilltime.presentation.screens.home

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import com.muhammad.pilltime.R
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.pilltime.presentation.components.AppAlertDialog
import com.muhammad.pilltime.presentation.navigation.Destinations
import com.muhammad.pilltime.presentation.screens.home.components.HomeHeader
import com.muhammad.pilltime.presentation.screens.home.components.medicationDataSection
import com.muhammad.pilltime.utils.openAppSettings
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navHostController: NavHostController, viewModel: HomeViewModel = koinViewModel()) {
    val context = LocalContext.current
    val activity = context as Activity
    val layoutDirection = LocalLayoutDirection.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            val isNotificationPermissionPermanentlyDenied =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    !isGranted && !ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                } else false
            if (isNotificationPermissionPermanentlyDenied) {
                viewModel.onAction(HomeAction.OnNotificationPermissionPermanentlyDenied)
            }
        }
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationPermissionGranted = ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!notificationPermissionGranted) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(
                start = paddingValues.calculateLeftPadding(layoutDirection = layoutDirection),
                end = paddingValues.calculateEndPadding(layoutDirection = layoutDirection),
                bottom = paddingValues.calculateBottomPadding(),
            ), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item("HomeHeader") {
                HomeHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(), onAddMedicationScreen = {
                        navHostController.navigate(Destinations.AddMedicationScreen)
                    }
                )
            }
            medicationDataSection(
                medications = state.medications,
                onDateSelected = { date ->
                    viewModel.onAction(HomeAction.OnFilterDataSelected(date))
                },
                selectedDate = state.selectedFilterDate
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
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            },
            message = stringResource(R.string.allow_notification_desp),
            confirmText = stringResource(R.string.allow),
            dismissText = stringResource(R.string.denied), onConfirmClick = {
                viewModel.onAction(HomeAction.OnToggleAllowNotificationAccessDialog)
                openAppSettings(context)
            }, onDismissClick = {
                viewModel.onAction(HomeAction.OnToggleAllowNotificationAccessDialog)
            }
        )
    }
}