package com.muhammad.pilltime.presentation.screens.add_medication

import com.muhammad.pilltime.domain.model.Medicine

sealed interface AddMedicationEvent{
    data class OnCreateMedicationSuccess(val medicine : Medicine) : AddMedicationEvent
}