package com.medicine.reminder.feature.medicationconfirm.usecase

import com.medicine.reminder.domain.model.Medication
import com.medicine.reminder.domain.repository.MedicationRepository
import javax.inject.Inject

class AddMedicationUseCase @Inject constructor(
    private val repository: MedicationRepository
) {
    suspend fun addMedication(medications: List<Medication>) {
        repository.insertMedications(medications)
    }
}
