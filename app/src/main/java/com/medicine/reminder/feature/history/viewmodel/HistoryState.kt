package com.medicine.reminder.feature.history.viewmodel

import com.medicine.reminder.domain.model.Medication

data class HistoryState(
    val medications: List<Medication> = emptyList()
)
