package com.muhammad.pilltime.domain.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
data class Boarding(
    val image : Int,
    @get:StringRes val title : Int,
    @get:StringRes  val description : Int
)