package com.medicine.reminder.feature.addmedication.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.medicine.reminder.core.navigation.DoseNavigationDestination
import com.medicine.reminder.domain.model.Medication
import com.medicine.reminder.feature.addmedication.AddMedicationRoute
import com.medicine.reminder.feature.home.navigation.ASK_ALARM_PERMISSION
import com.medicine.reminder.feature.home.navigation.ASK_NOTIFICATION_PERMISSION

object AddMedicationDestination : DoseNavigationDestination {
    override val route = "add_medication_route"
    override val destination = "add_medication_destination"
}

fun NavGraphBuilder.addMedicationGraph(navController: NavController, bottomBarVisibility: MutableState<Boolean>, fabVisibility: MutableState<Boolean>, onBackClicked: () -> Unit, navigateToMedicationConfirm: (List<Medication>) -> Unit) {
    composable(route = AddMedicationDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabVisibility.value = false
        }

        navController.previousBackStackEntry?.savedStateHandle.apply {
            this?.set(ASK_NOTIFICATION_PERMISSION, true)
        }
        navController.previousBackStackEntry?.savedStateHandle.apply {
            this?.set(ASK_ALARM_PERMISSION, true)
        }
        AddMedicationRoute(onBackClicked, navigateToMedicationConfirm)
    }
}
