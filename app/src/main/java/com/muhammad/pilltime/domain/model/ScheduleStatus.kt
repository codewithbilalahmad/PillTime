package com.muhammad.pilltime.domain.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.muhammad.pilltime.R

@Immutable
enum class ScheduleStatus(@get:StringRes val label : Int, val icon : Int){
    PENDING(label = R.drawable.ic_pending, icon = R.drawable.ic_pending),
    DONE(label = R.drawable.ic_done, icon = R.drawable.ic_done),
    MISSED(label = R.drawable.ic_missed, icon = R.drawable.ic_missed),
}