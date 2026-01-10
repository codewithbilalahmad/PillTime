package com.muhammad.pilltime.data.repository

import com.muhammad.pilltime.data.local.dao.MedicineDto
import com.muhammad.pilltime.data.mapper.toMedicine
import com.muhammad.pilltime.data.mapper.toMedicineEntities
import com.muhammad.pilltime.domain.model.Medicine
import com.muhammad.pilltime.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MedicationRepositoryImp(
    private val medicineDto: MedicineDto
) : MedicationRepository {
    override suspend fun insertMedicine(medicine: Medicine) {
        medicineDto.insertMedicine(medicine.toMedicineEntities())
    }

    override suspend fun updateMedicine(medicine: Medicine) {
        medicineDto.deleteMedicineByGroupId(medicine.id)
        medicineDto.insertMedicine(medicine.toMedicineEntities())
    }

   override suspend fun deleteMedicineByGroupId(groupId : Long) {
       medicineDto.deleteMedicineByGroupId(groupId)
    }

    override fun getAllMedicines(): Flow<List<Medicine>> {
        return medicineDto.getAllMedicines().map { entities ->
            entities.groupBy { it.medicineGroupId }.map { (_, group) ->
                group.toMedicine()
            }
        }
    }

    override suspend fun getMedicineByGroupId(groupId: Long): Flow<Medicine?> {
        return medicineDto.getMedicineByGroupId(groupId).map { entities ->
            entities.toMedicine()
        }
    }
}