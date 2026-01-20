package com.muhammad.pilltime.domain.model

import androidx.compose.runtime.Immutable
import com.muhammad.pilltime.utils.UiText

@Immutable
data class ScheduleDaySection(
    val dateHeader : UiText,
    val schedulesHistory: List<ScheduleHistory>
)