package com.muhammad.pilltime.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.muhammad.pilltime.data.local.entity.MedicineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDto {
    @Query("SELECT * FROM MedicineEntity ORDER BY createdAt DESC")
    fun getAllMedicines(): Flow<List<MedicineEntity>>

    @Insert
    suspend fun insertMedicine(medicineEntity: MedicineEntity)

    @Update
    suspend fun updateMedicine(medicineEntity: MedicineEntity)

    @Query("SELECT * FROM MedicineEntity WHERE id=:id")
    fun getMedicineById(id : Long) : Flow<MedicineEntity?>

    @Query("DELETE FROM MedicineEntity WHERE id=:id")
    suspend fun deleteMedicineById(id: Long)
}