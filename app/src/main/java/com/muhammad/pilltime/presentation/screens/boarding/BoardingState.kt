package com.muhammad.pilltime.presentation.screens.boarding

import com.muhammad.pilltime.R
import com.muhammad.pilltime.domain.model.Boarding

data class BoardingState(
    val boardingItems: List<Boarding> = listOf(
        Boarding(
            image = R.drawable.boarding_medicine,
            title = R.string.boarding_medicine_title,
            description = R.string.boarding_medicine_description
        ),
        Boarding(
            image = R.drawable.boarding_alarm,
            title = R.string.boarding_alarm_title,
            description = R.string.boarding_alarm_description
        ),
        Boarding(
            image = R.drawable.boarding_shield,
            title = R.string.boarding_health_title,
            description = R.string.boarding_health_description
        ),
    )
)