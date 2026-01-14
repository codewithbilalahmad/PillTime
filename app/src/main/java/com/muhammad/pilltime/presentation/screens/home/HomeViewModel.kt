package com.muhammad.pilltime.presentation.screens.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.pilltime.PillTimeApplication
import com.muhammad.pilltime.domain.model.ScheduleStatus
import com.muhammad.pilltime.domain.repository.MedicationRepository
import com.muhammad.pilltime.domain.repository.MedicationScheduleRespository
import com.muhammad.pilltime.domain.repository.SettingPreferences
import com.muhammad.pilltime.utils.canScheduleExactAlarms
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class HomeViewModel(
    private val medicationRepository: MedicationRepository,
    private val settingPreferences: SettingPreferences,
    private val medicationScheduleRepository: MedicationScheduleRespository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = combine(
        _state,
        medicationRepository.getAllMedicines(),
        settingPreferences.observeShowBoarding(),
        settingPreferences.observeUsername()
    ) { state, medications, showBoarding, username ->
        _state.update {
            it.copy(
                showBoarding = showBoarding,
                username = username,
                isCheckingBoarding = false
            )
        }
        val filtered = medications.filter { medicine ->
            state.selectedFilterDate in medicine.startDate..medicine.endDate && medicine.schedules.any { it.date == state.selectedFilterDate }
        }.map { medicine ->
            val scheduleForDate = medicine.schedules.filter { it.date == state.selectedFilterDate }
            val doneCount = scheduleForDate.count { it.status == ScheduleStatus.DONE }
            val totalCount = scheduleForDate.size
            medicine.copy(
                schedules = scheduleForDate,
                showMedicineSchedule = state.expandedMedicineIds.contains(medicine.id),
                selectedDateProgress = if (totalCount == 0) 0f else doneCount.toFloat() / totalCount
            )
        }
        state.copy(medications = filtered, isMedicinesLoading = false)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState())
    private val _events = Channel<HomeEvent>()
    val events = _events.receiveAsFlow()
    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnFilterDataSelected -> onFilterDataSelected(action.date)
            HomeAction.OnToggleAllowNotificationAccessDialog -> onToggleAllowNotificationAccessDialog()
            is HomeAction.OnUpdateMedicineScheduleStatus -> onUpdateMedicineScheduleStatus(
                scheduleId = action.scheduleId,
                medicineId = action.medicineId,
                status = action.status
            )

            is HomeAction.OnToggleMedicineSchedules -> onToggleMedicineSchedules(medicineId = action.medicineId)
            is HomeAction.OnDeleteMedicineById -> onDeleteMedicineById(action.medicineId)
            HomeAction.OnToggleAllowNotificationAndRemindersAccessDialog -> onToggleAllowNotificationAndRemindersAccessDialog()
            HomeAction.OnToggleAllowRemindersAccessDialog -> onToggleAllowRemindersAccessDialog()
            HomeAction.OnCheckReminderPermissions -> onCheckReminderPermissions()
        }
    }

    private fun onCheckReminderPermissions() {
        viewModelScope.launch {
            val context = PillTimeApplication.INSTANCE
            val notificationGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else true
            val alarmPermissionGranted = canScheduleExactAlarms(context)
            when {
                !notificationGranted && !alarmPermissionGranted -> {
                    onToggleAllowNotificationAndRemindersAccessDialog()
                }

                !notificationGranted -> {
                    onToggleAllowNotificationAccessDialog()
                }

                !alarmPermissionGranted -> {
                    onToggleAllowRemindersAccessDialog()
                }
            }
        }
    }

    private fun onToggleAllowRemindersAccessDialog() {
        _state.update { it.copy(showRemindersAccessDialog = !it.showRemindersAccessDialog) }
    }

    private fun onToggleAllowNotificationAndRemindersAccessDialog() {
        _state.update { it.copy(showAllowNotificationAndRemindersAccessDialog = !it.showAllowNotificationAndRemindersAccessDialog) }
    }

    private fun onDeleteMedicineById(medicineId: Long) {
        viewModelScope.launch {
            medicationRepository.deleteMedicineById(medicineId)
            medicationScheduleRepository.deleteMedicineSchedulesByMedicineId(medicineId)
        }
    }

    private fun onToggleMedicineSchedules(medicineId: Long) {
        _state.update {
            val expanded = it.expandedMedicineIds.toMutableSet()
            if (expanded.contains(medicineId)) {
                expanded.remove(medicineId)
            } else {
                expanded.add(medicineId)
            }
            it.copy(expandedMedicineIds = expanded)
        }
    }

    private fun onUpdateMedicineScheduleStatus(
        scheduleId: Long,
        status: ScheduleStatus,
        medicineId: Long,
    ) {
        viewModelScope.launch {
            medicationScheduleRepository.updateMedicineScheduleStatus(
                scheduleId = scheduleId,
                status = status
            )
        }
        _events.trySend(HomeEvent.ScrollToMedicine(medicineId = medicineId))
        onAction(HomeAction.OnToggleMedicineSchedules(medicineId = medicineId))
    }


    private fun onToggleAllowNotificationAccessDialog() {
        _state.update { it.copy(showAllowNotificationAccessDialog = !it.showAllowNotificationAccessDialog) }
    }

    private fun onFilterDataSelected(date: LocalDate) {
        _state.update { it.copy(selectedFilterDate = date) }
    }
}