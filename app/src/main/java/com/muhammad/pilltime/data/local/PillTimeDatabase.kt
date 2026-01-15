package com.muhammad.pilltime.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.muhammad.pilltime.data.local.converters.MedicineFrequencyConverter
import com.muhammad.pilltime.data.local.converters.MedicineTypeConverter
import com.muhammad.pilltime.data.local.converters.ScheduleStatusConverter
import com.muhammad.pilltime.data.local.dao.MedicineDao
import com.muhammad.pilltime.data.local.dao.MedicineScheduleDao
import com.muhammad.pilltime.data.local.dao.ScheduleHistoryDao
import com.muhammad.pilltime.data.local.entity.MedicineEntity
import com.muhammad.pilltime.data.local.entity.MedicineScheduleEntity
import com.muhammad.pilltime.data.local.entity.ScheduleHistoryEntity

@Database(
    entities = [MedicineEntity::class, MedicineScheduleEntity::class, ScheduleHistoryEntity::class],
    version = 9,
    exportSchema = false
)
@TypeConverters(
    MedicineTypeConverter::class, MedicineFrequencyConverter::class,
    ScheduleStatusConverter::class
)
abstract class PillTimeDatabase : RoomDatabase() {
    abstract val medicineDao: MedicineDao
    abstract val medicineScheduleDao: MedicineScheduleDao
    abstract val scheduleHistoryDao : ScheduleHistoryDao
}