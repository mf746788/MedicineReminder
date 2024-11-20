package com.medicine.reminder.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.medicine.reminder.feature.addmedication.navigation.addMedicationGraph
import com.medicine.reminder.feature.calendar.navigation.calendarGraph
import com.medicine.reminder.feature.history.historyGraph
import com.medicine.reminder.feature.home.navigation.HomeDestination
import com.medicine.reminder.feature.home.navigation.homeGraph
import com.medicine.reminder.feature.medicationconfirm.navigation.MEDICATION
import com.medicine.reminder.feature.medicationconfirm.navigation.MedicationConfirmDestination
import com.medicine.reminder.feature.medicationconfirm.navigation.medicationConfirmGraph
import com.medicine.reminder.feature.medicationdetail.MedicationDetailDestination
import com.medicine.reminder.feature.medicationdetail.medicationDetailGraph
import com.medicine.reminder.util.navigateSingleTop

@Composable
fun DoseNavHost(
    bottomBarVisibility: MutableState<Boolean>,
    fabVisibility: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HomeDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabVisibility = fabVisibility,
            navigateToMedicationDetail = {
                val bundle = Bundle()
                bundle.putParcelable(MEDICATION, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(MEDICATION, bundle)
                }
                navController.navigate(MedicationDetailDestination.route)
            }
        )
        historyGraph(
            bottomBarVisibility = bottomBarVisibility,
            fabVisibility = fabVisibility,
            navigateToMedicationDetail = {
                val bundle = Bundle()
                bundle.putParcelable(MEDICATION, it)
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(MEDICATION, bundle)
                }
                navController.navigate(MedicationDetailDestination.route)
            }
        )
        medicationDetailGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabVisibility = fabVisibility,
            onBackClicked = { navController.navigateUp() }
        )
        calendarGraph(bottomBarVisibility, fabVisibility)
        addMedicationGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabVisibility = fabVisibility,
            onBackClicked = { navController.navigateUp() },
            navigateToMedicationConfirm = {
                // TODO: Replace with medication id
                val bundle = Bundle()
                bundle.putParcelableArrayList(MEDICATION, ArrayList(it))
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(MEDICATION, bundle)
                }
                navController.navigate(MedicationConfirmDestination.route)
            }
        )
        medicationConfirmGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabVisibility = fabVisibility,
            onBackClicked = { navController.navigateUp() },
            navigateToHome = {
                navController.navigateSingleTop(HomeDestination.route)
            }
        )
    }
}
