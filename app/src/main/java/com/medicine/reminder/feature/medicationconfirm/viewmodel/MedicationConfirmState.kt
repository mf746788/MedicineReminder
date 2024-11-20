package com.medicine.reminder.feature.medicationconfirm.viewmodel

import com.medicine.reminder.domain.model.Medication

data class MedicationConfirmState(
    val medications: List<Medication>
)
