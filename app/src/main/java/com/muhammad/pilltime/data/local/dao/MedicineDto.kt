package com.muhammad.pilltime.data.local.dao

import androidx.room.*
import com.muhammad.pilltime.data.local.entity.MedicineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDto {
    @Query("SELECT * FROM MedicineEntity ORDER BY createdAt DESC")
    fun getAllMedicines(): Flow<List<MedicineEntity>>

    @Query("SELECT * FROM MedicineEntity WHERE medicineGroupId=:groupId")
    fun getMedicineByGroupId(groupId: Long): Flow<List<MedicineEntity>>

    @Insert
    suspend fun insertMedicine(medicineEntities: List<MedicineEntity>)

    @Upsert
    suspend fun updateMedicine(medicineEntity: MedicineEntity)

    @Query("DELETE FROM MedicineEntity WHERE medicineGroupId=:groupId")
    suspend fun deleteMedicineByGroupId(groupId: Long)
}