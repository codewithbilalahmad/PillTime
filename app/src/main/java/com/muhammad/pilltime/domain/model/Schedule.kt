package com.muhammad.pilltime.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import java.util.UUID

@Immutable
@Serializable
data class Schedule(
    val id : String = UUID.randomUUID().toString(),
    val time : LocalTime
)
