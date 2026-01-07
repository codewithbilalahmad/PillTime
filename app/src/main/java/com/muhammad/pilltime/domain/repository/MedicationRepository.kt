package com.muhammad.pilltime.domain.repository

import com.muhammad.pilltime.domain.model.Medicine
import kotlinx.coroutines.flow.Flow

interface MedicationRepository {
    suspend fun upsertMedicine(medicine : Medicine)
    suspend fun deleteMedicine(medicine : Medicine)
    fun getAllMedicines() : Flow<List<Medicine>>
    suspend fun getMedicineById(id : Long) : Medicine?
}