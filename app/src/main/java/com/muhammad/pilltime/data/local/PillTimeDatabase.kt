package com.muhammad.pilltime.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.muhammad.pilltime.data.local.converters.MedicineFrequencyConverter
import com.muhammad.pilltime.data.local.converters.MedicineTypeConverter
import com.muhammad.pilltime.data.local.converters.ScheduleStatusConverter
import com.muhammad.pilltime.data.local.dao.MedicineDto
import com.muhammad.pilltime.data.local.dao.MedicineScheduleDao
import com.muhammad.pilltime.data.local.entity.MedicineEntity
import com.muhammad.pilltime.data.local.entity.MedicineScheduleEntity

@Database(
    entities = [MedicineEntity::class, MedicineScheduleEntity::class],
    version = 7,
    exportSchema = false
)
@TypeConverters(
    MedicineTypeConverter::class, MedicineFrequencyConverter::class,
    ScheduleStatusConverter::class
)
abstract class PillTimeDatabase : RoomDatabase() {
    abstract val medicineDao: MedicineDto
    abstract val medicineScheduleDao : MedicineScheduleDao
}