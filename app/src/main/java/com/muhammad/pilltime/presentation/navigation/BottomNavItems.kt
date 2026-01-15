package com.muhammad.pilltime.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.muhammad.pilltime.R

@Immutable
enum class BottomNavItems(
    @get:StringRes val label: Int,
    val selectedIcon: Int,
    val unSelectedIcon: Int,
    val route: Destinations,
) {
    Home(
        label = R.string.home,
        selectedIcon = R.drawable.ic_home_filled,
        unSelectedIcon = R.drawable.ic_home_outlined,
        route = Destinations.HomeScreen
    ),
    History(
        label = R.string.history,
        selectedIcon = R.drawable.ic_history_filled,
        unSelectedIcon = R.drawable.ic_history_outlined,
        route = Destinations.MedicationHistoryScreen
    )
}