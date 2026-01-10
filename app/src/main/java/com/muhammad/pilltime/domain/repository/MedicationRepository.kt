package com.muhammad.pilltime.domain.repository

import com.muhammad.pilltime.domain.model.Medicine
import kotlinx.coroutines.flow.Flow

interface MedicationRepository {
    suspend fun insertMedicine(medicine: Medicine)
    suspend fun updateMedicine(medicine : Medicine)
    suspend fun deleteMedicineByGroupId(groupId : Long)
    fun getAllMedicines() : Flow<List<Medicine>>
    suspend fun getMedicineByGroupId(groupId: Long): Flow<Medicine?>
}