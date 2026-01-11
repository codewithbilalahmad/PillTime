package com.muhammad.pilltime.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.muhammad.pilltime.domain.model.Medicine
import com.muhammad.pilltime.presentation.screens.add_medication.AddMedicationScreen
import com.muhammad.pilltime.presentation.screens.add_medication_success.AddMedicationSuccessScreen
import com.muhammad.pilltime.presentation.screens.home.HomeScreen
import com.muhammad.pilltime.presentation.screens.home.HomeViewModel
import kotlin.reflect.typeOf

@Composable
fun AppNavigation(navHostController: NavHostController, homeViewModel: HomeViewModel) {
    NavHost(navController = navHostController, startDestination = Destinations.HomeScreen) {
        composable<Destinations.HomeScreen>{
            HomeScreen(navHostController = navHostController, viewModel = homeViewModel)
        }
        composable<Destinations.AddMedicationScreen>{
            AddMedicationScreen(navHostController = navHostController)
        }
        composable<Destinations.AddMedicationSuccessScreen>(typeMap = mapOf(
            typeOf<Medicine>() to CustomNavType.Medicine
        )){
            val medicine = it.toRoute<Destinations.AddMedicationSuccessScreen>().medicine
            AddMedicationSuccessScreen(navHostController = navHostController, medicine = medicine)
        }
        composable<Destinations.MedicationHistoryScreen>{  }
    }
}