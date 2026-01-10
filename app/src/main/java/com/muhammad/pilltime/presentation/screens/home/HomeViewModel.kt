package com.muhammad.pilltime.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.pilltime.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate

class HomeViewModel(
    private val medicationRepository: MedicationRepository
) : ViewModel(){
    private val _state = MutableStateFlow(HomeState())
    val state = combine(
        _state,medicationRepository.getAllMedicines()
    ){state, medications ->
        val filteredMedication = medications.filter {medicine ->
            state.selectedFilterDate >= medicine.startDate && state.selectedFilterDate <= medicine.endDate
        }
        state.copy(medications = filteredMedication)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState())
    fun onAction(action : HomeAction){
        when(action){
            is HomeAction.OnFilterDataSelected -> onFilterDataSelected(action.date)
            HomeAction.OnNotificationPermissionPermanentlyDenied -> onNotificationPermissionPermanentlyDenied()
            HomeAction.OnToggleAllowNotificationAccessDialog -> onToggleAllowNotificationAccessDialog()
        }
    }

    private fun onNotificationPermissionPermanentlyDenied() {
        _state.update {
            it.copy(
                isNotificationPermanentlyDenied = true,
                showAllowNotificationAccessDialog = true
            )
        }
    }

    private fun onToggleAllowNotificationAccessDialog() {
        _state.update { it.copy(showAllowNotificationAccessDialog = !it.showAllowNotificationAccessDialog) }
    }

    private fun onFilterDataSelected(date: LocalDate) {
        _state.update { it.copy(selectedFilterDate = date) }
    }
}