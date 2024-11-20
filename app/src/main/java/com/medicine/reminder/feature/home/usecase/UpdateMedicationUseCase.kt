package com.medicine.reminder.feature.home.usecase

import com.medicine.reminder.domain.model.Medication
import com.medicine.reminder.domain.repository.MedicationRepository
import javax.inject.Inject

class UpdateMedicationUseCase @Inject constructor(
    private val repository: MedicationRepository
) {

    suspend fun updateMedication(medication: Medication) {
        return repository.updateMedication(medication)
    }
}
