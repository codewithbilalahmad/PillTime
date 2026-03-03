package com.muhammad.pilltime.presentation.screens.boarding

import com.muhammad.pilltime.R
import com.muhammad.pilltime.domain.model.Boarding

data class BoardingState(
    val boardingItems: List<Boarding> = listOf(
        Boarding(
            index = 0,
            image = R.drawable.boarding_medicine,
            title = R.string.boarding_medicine_title,
            description = R.string.boarding_medicine_description
        ),
        Boarding(
            index = 1,
            image = R.drawable.boarding_alarm,
            title = R.string.boarding_alarm_title,
            description = R.string.boarding_alarm_description
        ),
        Boarding(
            index = 2,
            image = R.drawable.boarding_shield,
            title = R.string.boarding_health_title,
            description = R.string.boarding_health_description
        )
    )
)