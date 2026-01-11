package com.muhammad.pilltime.presentation.screens.home

sealed interface HomeEvent{
    data class ScrollToMedicine(val medicineId : Long) : HomeEvent
}