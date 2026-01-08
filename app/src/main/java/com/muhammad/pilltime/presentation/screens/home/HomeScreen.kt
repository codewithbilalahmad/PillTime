package com.muhammad.pilltime.presentation.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.NavHostController
import com.muhammad.pilltime.presentation.navigation.Destinations
import com.muhammad.pilltime.presentation.screens.home.components.HomeHeader
import com.muhammad.pilltime.presentation.screens.home.components.medicationDataSection

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val layoutDirection = LocalLayoutDirection.current
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(
                start = paddingValues.calculateLeftPadding(layoutDirection = layoutDirection),
                end = paddingValues.calculateEndPadding(layoutDirection = layoutDirection),
                bottom = paddingValues.calculateBottomPadding(),
            )
        ) {
            item("HomeHeader") {
                HomeHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(), onAddMedicationScreen = {
                            navHostController.navigate(Destinations.AddMedicationScreen)
                    }
                )
            }
            medicationDataSection()
        }
    }
}