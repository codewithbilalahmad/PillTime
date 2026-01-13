package com.muhammad.pilltime.presentation.screens.boarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BoardingViewModel : ViewModel() {
    private val _state = MutableStateFlow(BoardingState())
    val state = _state.asStateFlow()
}