package com.muhammad.pilltime.domain.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.muhammad.pilltime.R

@Immutable
enum class MedicineType(@get:StringRes val label : Int, val icon : Int) {
    TABLET(R.string.tablet, icon = R.drawable.ic_tablet),
    CAPSULE(R.string.capsule, icon = R.drawable.ic_capsule),
    SYRUP(R.string.syrup, icon = R.drawable.ic_syrup),
    DROPS(R.string.drops, icon = R.drawable.ic_drops),
    Spray(R.string.spray, icon = R.drawable.ic_spray),
    GEL(R.string.gel, icon = R.drawable.ic_gel),
}