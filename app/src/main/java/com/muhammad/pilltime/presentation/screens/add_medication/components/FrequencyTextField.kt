package com.muhammad.pilltime.presentation.screens.add_medication.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.pilltime.R
import com.muhammad.pilltime.domain.model.MedicineFrequency
import com.muhammad.pilltime.presentation.components.AppCheckBox
import com.muhammad.pilltime.presentation.components.AppTextField

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun FrequencyTextField(
    modifier: Modifier = Modifier,
    showFrequencyOptionsDropDown: Boolean,
    frequency: MedicineFrequency,
    onFrequencyChange: (MedicineFrequency) -> Unit,
    onToggleFrequencyOptionsDropDown: () -> Unit,
) {
    val dropdownHeight = LocalConfiguration.current.screenHeightDp.dp * 0.4f
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.frequency),
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            AppTextField(
                value = stringResource(frequency.label),
                trailingIcon = R.drawable.ic_arrow_down,
                enabled = false,
                onClick = onToggleFrequencyOptionsDropDown,
                hint = R.string.medication_frequency,
                modifier = Modifier.fillMaxWidth(),
                onTrailingIconClick = onToggleFrequencyOptionsDropDown
            )
            DropdownMenu(
                expanded = showFrequencyOptionsDropDown,
                onDismissRequest = onToggleFrequencyOptionsDropDown,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dropdownHeight),
                containerColor = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 4.dp,
                tonalElevation = 4.dp
            ) {
                MedicineFrequency.entries.forEach { item ->
                    val isSelected = item == frequency
                    DropdownMenuItem(
                        text = { Text(stringResource(item.label)) },
                        onClick = {
                            onFrequencyChange(item)
                        }, leadingIcon = {
                            AnimatedVisibility(
                                isSelected,
                                enter = scaleIn(animationSpec = MaterialTheme.motionScheme.fastEffectsSpec()),
                                exit = scaleOut(animationSpec = MaterialTheme.motionScheme.fastEffectsSpec())
                            ) {
                                AppCheckBox(checked = true, onCheckChange = {})
                            }
                        }
                    )
                }
            }
        }
    }
}
