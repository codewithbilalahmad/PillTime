package com.muhammad.pilltime.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.muhammad.pilltime.data.local.converters.MedicineFrequencyConverter
import com.muhammad.pilltime.data.local.converters.MedicineTypeConverter
import com.muhammad.pilltime.data.local.dao.MedicineDto
import com.muhammad.pilltime.data.local.entity.MedicineEntity

@Database(
    entities = [MedicineEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(MedicineTypeConverter::class, MedicineFrequencyConverter::class)
abstract class PillTimeDatabase : RoomDatabase(){
    abstract val medicineDao : MedicineDto
}