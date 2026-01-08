package com.muhammad.pilltime.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalTime
import java.util.UUID

@Immutable
data class Schedule(
    val id : String = UUID.randomUUID().toString(),
    val time : LocalTime
)
