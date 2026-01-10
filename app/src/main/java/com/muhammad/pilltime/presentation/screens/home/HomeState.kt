package com.muhammad.pilltime.presentation.screens.home

import com.muhammad.pilltime.domain.model.Medicine
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class HomeState(
    val medications: List<Medicine> = emptyList(),
    val selectedFilterDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val isNotificationPermanentlyDenied : Boolean = false,
    val showAllowNotificationAccessDialog : Boolean = false
)