package com.muhammad.pilltime.data.repository

import com.muhammad.pilltime.data.local.dao.MedicineDto
import com.muhammad.pilltime.data.local.dao.MedicineScheduleDao
import com.muhammad.pilltime.data.mapper.toMedicine
import com.muhammad.pilltime.data.mapper.toMedicineEntity
import com.muhammad.pilltime.data.mapper.toMedicineSchedule
import com.muhammad.pilltime.domain.model.Medicine
import com.muhammad.pilltime.domain.repository.MedicationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MedicationRepositoryImp(
    private val medicineDto: MedicineDto,
    private val medicineScheduleDao: MedicineScheduleDao,
) : MedicationRepository {
    override suspend fun insertMedicine(medicine: Medicine) {
        medicineDto.insertMedicine(medicine.toMedicineEntity())
    }

    override suspend fun updateMedicine(medicine: Medicine) {
        medicineDto.updateMedicine(medicine.toMedicineEntity())
    }

    override suspend fun deleteMedicineById(medicineId: Long) {
        medicineDto.deleteMedicineById(medicineId)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllMedicines(): Flow<List<Medicine>> {
        return medicineDto.getAllMedicines().flatMapLatest { medicinesEntities ->
            if (medicinesEntities.isEmpty()) {
                flowOf(emptyList())
            } else {
                combine(medicinesEntities.map { medicineEntity ->
                    medicineScheduleDao.getMedicineSchedules(medicineEntity.id)
                        .map { scheduleEntities ->
                            medicineEntity.toMedicine(scheduleEntities.map { it.toMedicineSchedule() })
                        }
                }) { medicines ->
                    medicines.toList()
                }
            }
        }
    }

    override suspend fun getMedicineById(medicineId: Long): Flow<Medicine?> {
        return combine(
            medicineDto.getMedicineById(medicineId),
            medicineScheduleDao.getMedicineSchedules(medicineId)
        ) { medicineEntity, scheduleEntities ->
            medicineEntity?.toMedicine(scheduleEntities.map { it.toMedicineSchedule() })
        }
    }
}