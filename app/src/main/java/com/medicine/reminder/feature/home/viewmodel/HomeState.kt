package com.medicine.reminder.feature.home.viewmodel

import com.medicine.reminder.domain.model.Medication

data class HomeState(
    val greeting: String = "",
    val userName: String = "",
    val lastSelectedDate: String = "",
    val medications: List<Medication> = emptyList()
)
