package com.muhammad.pilltime.data.local.dao

import androidx.room.*
import com.muhammad.pilltime.data.local.entity.MedicineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDto{
    @Query("SELECT * FROM MedicineEntity ORDER BY createdAt DESC")
    fun getAllMedicines(): Flow<List<MedicineEntity>>

    @Query("SELECT * FROM MedicineEntity WHERE id=:id")
    suspend fun getMedicineById(id: Long): MedicineEntity?

    @Upsert
    suspend fun upsertMedicine(medicineEntity: MedicineEntity)

    @Delete
    suspend fun deleteMedicine(medicineEntity: MedicineEntity)
}