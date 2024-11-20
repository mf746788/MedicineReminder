package com.medicine.reminder.data.mapper

import com.medicine.reminder.data.entity.MedicationEntity
import com.medicine.reminder.domain.model.Medication

fun MedicationEntity.toMedication(): Medication {
    return Medication(
        id = id,
        name = name,
        dosage = dosage,
        recurrence = recurrence,
        endDate = endDate,
        medicationTime = medicationTime,
        medicationTaken = medicationTaken
    )
}

fun Medication.toMedicationEntity(): MedicationEntity {
    return MedicationEntity(
        id = id ?: 0L,
        name = name,
        dosage = dosage,
        recurrence = recurrence,
        endDate = endDate,
        medicationTime = medicationTime,
        medicationTaken = medicationTaken
    )
}
