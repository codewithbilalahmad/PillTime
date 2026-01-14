package com.muhammad.pilltime.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingPreferences {
    suspend fun saveShowBoarding(show: Boolean)
    fun observeShowBoarding(): Flow<Boolean>
    suspend fun saveUsername(name: String)
    fun observeUsername(): Flow<String>
}