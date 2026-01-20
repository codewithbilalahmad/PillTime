package com.muhammad.pilltime.presentation.screens.medication_history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.pilltime.domain.model.ScheduleDaySection
import com.muhammad.pilltime.presentation.components.DashedHorizontalDivider

fun LazyListScope.scheduleHistoryList(
    scheduleDaySections: List<ScheduleDaySection>,
) {
    scheduleDaySections.forEachIndexed { index, section ->
        stickyHeader {
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashedHorizontalDivider(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = section.dateHeader.asString().uppercase(), style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold))
                }
                DashedHorizontalDivider(modifier = Modifier.weight(1f))
            }
            Spacer(Modifier.height(8.dp))
        }
        items(section.schedulesHistory, key = { it.id }, contentType = {
            "schedulesHistory${it.id}"
        }) { scheduleHistory ->
            ScheduleHistoryCard(
                scheduleHistory = scheduleHistory,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}