package com.muhammad.pilltime.presentation.screens.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muhammad.pilltime.presentation.components.WeekCalendar

fun LazyListScope.medicationDataSection() {
    item {
        WeekCalendar(
            selectedDate = null,
            onDateSelected = {},
            modifier = Modifier
                .fillMaxWidth().animateItem()
                .padding(horizontal = 16.dp)
        )
    }
}