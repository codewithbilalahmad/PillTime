package com.muhammad.pilltime

import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.muhammad.pilltime.domain.model.ScheduleStatus
import com.muhammad.pilltime.presentation.navigation.AppNavigation
import com.muhammad.pilltime.presentation.screens.home.HomeAction
import com.muhammad.pilltime.presentation.screens.home.HomeViewModel
import com.muhammad.pilltime.presentation.theme.PillTimeTheme
import com.muhammad.pilltime.utils.Constants.DONE_REMINDER_ACTION
import com.muhammad.pilltime.utils.Constants.MEDICINE_ID
import com.muhammad.pilltime.utils.Constants.MISSED_REMINDER_ACTION
import com.muhammad.pilltime.utils.Constants.NOTIFICATION_ID
import com.muhammad.pilltime.utils.Constants.SCHEDULE_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val homeViewModel: HomeViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            homeViewModel.state.value.isCheckingBoarding
        }
        handleReminderAction(intent)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        setContent {
            PillTimeTheme {
                val state by homeViewModel.state.collectAsStateWithLifecycle()
                val navHostController = rememberNavController()
                AppNavigation(
                    navHostController = navHostController,
                    homeViewModel = homeViewModel,
                    showBoarding = state.showBoarding
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleReminderAction(intent)
    }

    private fun handleReminderAction(intent: Intent) {
        val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val scheduleId = intent.getLongExtra(SCHEDULE_ID, -1L)
        val medicineId = intent.getLongExtra(MEDICINE_ID, -1L)
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, 0)
        if (scheduleId == -1L) return

        notificationManager.cancel(notificationId)
        when (intent.action) {
            DONE_REMINDER_ACTION -> {
                homeViewModel.onAction(
                    HomeAction.OnUpdateMedicineScheduleStatus(
                        scheduleId = scheduleId,
                        medicineId = medicineId,
                        status = ScheduleStatus.DONE
                    )
                )
            }

            MISSED_REMINDER_ACTION -> {
                homeViewModel.onAction(
                    HomeAction.OnUpdateMedicineScheduleStatus(
                        scheduleId = scheduleId,
                        medicineId = medicineId,
                        status = ScheduleStatus.MISSED
                    )
                )
            }
        }
    }
}