package com.muhammad.pilltime.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navHostController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = paddingValues) {
            item("HomeHeader") {
                HomeHeader(modifier = Modifier
                    .fillMaxWidth()
                    .animateItem())
            }
        }
    }
}