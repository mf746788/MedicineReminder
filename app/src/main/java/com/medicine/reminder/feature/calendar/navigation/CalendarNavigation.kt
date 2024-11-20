package com.medicine.reminder.feature.calendar.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.medicine.reminder.core.navigation.DoseNavigationDestination
import com.medicine.reminder.feature.calendar.CalendarRoute

object CalendarDestination : DoseNavigationDestination {
    override val route = "calendar_route"
    override val destination = "calendar_destination"
}

fun NavGraphBuilder.calendarGraph(bottomBarVisibility: MutableState<Boolean>, fabVisibility: MutableState<Boolean>) {
    composable(route = CalendarDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = true
            fabVisibility.value = false
        }
        CalendarRoute()
    }
}
