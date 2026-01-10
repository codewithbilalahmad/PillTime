package com.muhammad.pilltime.domain.repository

import com.muhammad.pilltime.domain.model.Medicine

interface NotificationScheduler{
    fun scheduleMedicine(medicine : Medicine)
    fun cancelMedicine(medicineId : Long)
}