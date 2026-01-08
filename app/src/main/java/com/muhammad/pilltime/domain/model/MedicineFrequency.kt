package com.muhammad.pilltime.domain.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.muhammad.pilltime.R

@Immutable
enum class MedicineFrequency(@get:StringRes val label : Int) {
    EVERYDAY(R.string.every_day),
    EVERY_2_DAYS(R.string.every_2_days),
    EVERY_3_DAYS(R.string.every_3_days),
    EVERY_4_DAYS(R.string.every_4_days),
    EVERY_5_DAYS(R.string.every_5_days),
    EVERY_6_DAYS(R.string.every_6_days),
    EVERY_WEEK(R.string.every_week),
    EVERY_2_WEEK(R.string.every_2_week),
    EVERY_3_WEEK(R.string.every_3_week),
    EVERY_MONTH(R.string.every_month)
}
