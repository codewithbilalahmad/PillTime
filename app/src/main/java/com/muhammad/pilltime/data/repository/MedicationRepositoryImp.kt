package com.muhammad.pilltime.data.repository

import com.muhammad.pilltime.data.local.dao.MedicineDto
import com.muhammad.pilltime.data.mapper.toMedicine
import com.muhammad.pilltime.data.mapper.toMedicineEntity
import com.muhammad.pilltime.domain.model.Medicine
import com.muhammad.pilltime.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MedicationRepositoryImp(
    private val medicineDto: MedicineDto
) : MedicationRepository {
    override suspend fun upsertMedicine(medicine: Medicine) {
        medicineDto.upsertMedicine(medicine.toMedicineEntity())
    }

    override suspend fun deleteMedicine(medicine: Medicine) {
        medicineDto.deleteMedicine(medicine.toMedicineEntity())
    }

    override fun getAllMedicines(): Flow<List<Medicine>> {
        return medicineDto.getAllMedicines().map { entities -> entities.map { it.toMedicine() } }
    }

    override suspend fun getMedicineById(id: Long): Medicine? {
        return medicineDto.getMedicineById(id)?.toMedicine()
    }
}