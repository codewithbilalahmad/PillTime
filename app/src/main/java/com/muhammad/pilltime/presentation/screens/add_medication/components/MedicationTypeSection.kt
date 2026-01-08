package com.muhammad.pilltime.presentation.screens.add_medication.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.pilltime.R
import com.muhammad.pilltime.domain.model.MedicineType

@Composable
fun MedicationTypeSection(
    modifier: Modifier = Modifier,
    onSelectMedicationType: (MedicineType) -> Unit,
    selectedMedicineType: MedicineType,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = stringResource(R.string.medication_type),
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            maxItemsInEachRow = 3
        ) {
            MedicineType.entries.forEach { type ->
                val isSelected = type == selectedMedicineType
                MedicationTypeCard(
                    type = type,
                    onClick = {
                        onSelectMedicationType(type)
                    }, modifier = Modifier.weight(1f),
                    isSelected = isSelected
                )
            }
        }
    }
}