package com.muhammad.pilltime.presentation.screens.home

import com.muhammad.pilltime.domain.model.ScheduleStatus
import kotlinx.datetime.LocalDate


sealed interface HomeAction{
    data object OnCheckReminderPermissions :  HomeAction
    data class OnFilterDataSelected(val date : LocalDate) : HomeAction
    data object OnToggleAllowNotificationAccessDialog : HomeAction
    data object OnToggleAllowNotificationAndRemindersAccessDialog : HomeAction
    data object OnToggleAllowRemindersAccessDialog : HomeAction
    data class OnUpdateMedicineScheduleStatus(val scheduleId: Long,val medicineId : Long, val status: ScheduleStatus) : HomeAction
    data class OnToggleMedicineSchedules(val medicineId : Long) : HomeAction
    data class OnDeleteMedicineById(val medicineId : Long) : HomeAction
}