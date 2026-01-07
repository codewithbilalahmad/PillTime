package com.muhammad.pilltime.data.local.converters

import androidx.room.TypeConverter
import com.muhammad.pilltime.domain.model.MedicineType


class MedicineTypeConverter {
    @TypeConverter
    fun toMedicineType(name : String) : MedicineType{
        return MedicineType.valueOf(name)
    }

    @TypeConverter
    fun fromMedicineType(type : MedicineType) : String{
        return type.name
    }
}