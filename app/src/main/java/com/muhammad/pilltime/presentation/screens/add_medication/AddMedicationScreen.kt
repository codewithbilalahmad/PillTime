package com.muhammad.pilltime.presentation.screens.add_medication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.pilltime.R
import com.muhammad.pilltime.presentation.components.AppTextField
import com.muhammad.pilltime.presentation.screens.add_medication.components.MedicationTypeSection
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddMedicationScreen(
    navHostController: NavHostController,
    viewModel: AddMedicationViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val layoutDirection = LocalLayoutDirection.current
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(stringResource(R.string.new_medicaton))
            },
            navigationIcon = {
                IconButton(onClick = {
                    navHostController.navigateUp()
                }, shapes = IconButtonDefaults.shapes()) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 8.dp,
                start = paddingValues.calculateStartPadding(layoutDirection) + 12.dp,
                end = paddingValues.calculateEndPadding(layoutDirection) + 12.dp,
                bottom = paddingValues.calculateBottomPadding() + 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item("medication_name") {
                AppTextField(value = state.medicationName, onValueChange = { newValue ->
                    viewModel.onAction(AddMedicationAction.OnMedicationNameChange(newValue))
                }, modifier = Modifier.fillMaxWidth(), hint = R.string.medication_name)
            }
            item("medication_duration") {
                AppTextField(value = state.medicationName, onValueChange = { newValue ->
                    viewModel.onAction(AddMedicationAction.OnMedicationNameChange(newValue))
                }, modifier = Modifier.fillMaxWidth(), trailingIcon = R.drawable.ic_date)
            }
            item("MedicationTypeSection") {
                MedicationTypeSection(
                    modifier = Modifier.fillMaxWidth(),
                    onSelectMedicationType = { type ->
                        viewModel.onAction(AddMedicationAction.OnSelectMedicineType(type))
                    },
                    selectedMedicineType = state.selectedMedicineType
                )
            }
        }
    }
}