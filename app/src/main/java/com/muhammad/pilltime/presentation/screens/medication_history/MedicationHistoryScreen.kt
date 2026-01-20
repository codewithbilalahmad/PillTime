package com.muhammad.pilltime.presentation.screens.medication_history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.pilltime.R
import com.muhammad.pilltime.presentation.components.DashedHorizontalDivider
import com.muhammad.pilltime.presentation.components.WeekCalendar
import com.muhammad.pilltime.presentation.screens.medication_history.components.scheduleHistoryList
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MedicationHistoryScreen(
    navHostController: NavHostController,
    viewModel: MedicationHistoryViewModel = koinViewModel(),
) {
    val layoutDirection = LocalLayoutDirection.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.schedule_history))
                },
                actions = {
                    IconButton(onClick = {}, shapes = IconButtonDefaults.shapes()) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_menu),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
            DashedHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(
                start = paddingValues.calculateLeftPadding(layoutDirection) + 16.dp,
                end = paddingValues.calculateRightPadding(layoutDirection) + 16.dp,
                bottom = paddingValues.calculateBottomPadding(),
                top = paddingValues.calculateTopPadding() + 12.dp
            ), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item("WeekCalendar") {
                WeekCalendar(selectedDate = state.selectedFilterDate, onDateSelected = { date ->
                    viewModel.onAction(MedicationHistoryAction.OnFilterDateChange(date))
                })
            }
            scheduleHistoryList(scheduleDaySections = state.scheduleDaySections)
        }
    }
}