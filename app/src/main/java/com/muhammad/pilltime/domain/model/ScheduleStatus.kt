package com.muhammad.pilltime.domain.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.muhammad.pilltime.R

@Immutable
enum class ScheduleStatus(@get:StringRes val label : Int, val icon : Int){
    PENDING(label = R.string.pending, icon = R.drawable.ic_pending),
    DONE(label = R.string.done, icon = R.drawable.ic_done),
    MISSED(label = R.string.missed, icon = R.drawable.ic_missed),
}