package com.muhammad.pilltime.data.local.converters

import androidx.room.TypeConverter
import com.muhammad.pilltime.domain.model.MedicineFrequency

class MedicineFrequencyConverter{
    @TypeConverter
    fun fromMedicineFrequency(frequency: MedicineFrequency) : String{
        return frequency.name
    }
    @TypeConverter
    fun toMedicineFrequency(name : String) : MedicineFrequency{
        return MedicineFrequency.valueOf(name)
    }
}