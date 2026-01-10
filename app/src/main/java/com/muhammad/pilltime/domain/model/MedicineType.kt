package com.muhammad.pilltime.domain.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.muhammad.pilltime.R

@Immutable
enum class MedicineType(@get:StringRes val label : Int, val icon : Int, val color : Color) {
    TABLET(R.string.tablet, icon = R.drawable.ic_tablet, color = Color(0xFF81C784)),
    CAPSULE(R.string.capsule, icon = R.drawable.ic_capsule, color = Color(0xFF64B5F6)),
    SYRUP(R.string.syrup, icon = R.drawable.ic_syrup, color =  Color(0xFFBA68C8)),
    DROPS(R.string.drops, icon = R.drawable.ic_drops, color = Color(0xFF4DD0E1)),
    Spray(R.string.spray, icon = R.drawable.ic_spray, color = Color(0xFF4DB6AC)),
    GEL(R.string.gel, icon = R.drawable.ic_gel, color = Color(0xFFF06292))
}