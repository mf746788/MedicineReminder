package com.medicine.reminder.data.repository

import com.medicine.reminder.data.MedicationDao
import com.medicine.reminder.data.mapper.toMedication
import com.medicine.reminder.data.mapper.toMedicationEntity
import com.medicine.reminder.domain.model.Medication
import com.medicine.reminder.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MedicationRepositoryImpl(
    private val dao: MedicationDao
) : MedicationRepository {

    override suspend fun insertMedications(medications: List<Medication>) {
        medications.map { it.toMedicationEntity() }.forEach {
            dao.insertMedication(it)
        }
    }

    override suspend fun deleteMedication(medication: Medication) {
        dao.deleteMedication(medication.toMedicationEntity())
    }

    override suspend fun updateMedication(medication: Medication) {
        dao.updateMedication(medication.toMedicationEntity())
    }

    override fun getAllMedications(): Flow<List<Medication>> {
        return dao.getAllMedications().map { entities ->
            entities.map { it.toMedication() }
        }
    }

    override fun getMedicationsForDate(date: String): Flow<List<Medication>> {
        return dao.getMedicationsForDate(
            date = date
        ).map { entities ->
            entities.map { it.toMedication() }
        }
    }
}
