package com.muhammad.pilltime.data.local.converters

import androidx.room.TypeConverter
import com.muhammad.pilltime.domain.model.ScheduleStatus

class ScheduleStatusConverter{
    @TypeConverter
    fun fromScheduleStatus(status : ScheduleStatus) : String{
        return status.name
    }
    @TypeConverter
    fun toScheduleStatus(name : String) : ScheduleStatus{
        return ScheduleStatus.valueOf(name)
    }
}