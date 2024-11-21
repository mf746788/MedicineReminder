package com.medicine.reminder

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.medicine.reminder.analytics.AnalyticsEvents
import com.medicine.reminder.analytics.AnalyticsHelper
import com.medicine.reminder.feature.addmedication.navigation.AddMedicationDestination
import com.medicine.reminder.feature.history.HistoryDestination
import com.medicine.reminder.feature.home.navigation.HomeDestination
import com.medicine.reminder.navigation.DoseNavHost
import com.medicine.reminder.navigation.DoseTopLevelNavigation
import com.medicine.reminder.navigation.TOP_LEVEL_DESTINATIONS
import com.medicine.reminder.navigation.TopLevelDestination
import com.medicine.reminder.ui.theme.MedicineReminderTheme
import com.medicine.reminder.util.SnackbarUtil

@Composable
fun MedicineReminder(
    analyticsHelper: AnalyticsHelper
) {

    MedicineReminderTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController() // Correctly getting the navController
            val doseTopLevelNavigation = remember(navController) {
                DoseTopLevelNavigation(navController)
            }

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val bottomBarVisibility = rememberSaveable { mutableStateOf(true) }
            val fabVisibility = rememberSaveable { mutableStateOf(true) }

            Scaffold(
                modifier = Modifier.padding(16.dp, 20.dp),
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                floatingActionButton = {
                    AnimatedVisibility(
                        visible = fabVisibility.value,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it }),
                        content = {
                            DoseFAB(navController, analyticsHelper)
                        }
                    )
                },
                bottomBar = {
                    Box {
                        SnackbarUtil.SnackbarWithoutScaffold(
                            SnackbarUtil.getSnackbarMessage().component1(),
                            SnackbarUtil.isSnackbarVisible().component1()
                        ) {
                            SnackbarUtil.hideSnackbar()
                        }
                        AnimatedVisibility(
                            visible = bottomBarVisibility.value,
                            enter = slideInVertically(initialOffsetY = { it }),
                            exit = slideOutVertically(targetOffsetY = { it }),
                            content = {
                                DoseBottomBar(
                                    onNavigateToTopLevelDestination = doseTopLevelNavigation::navigateTo,
                                    currentDestination = currentDestination,
                                    analyticsHelper = analyticsHelper
                                )
                            }
                        )
                    }
                }
            ) { padding ->
                Row(
                    Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                        )
                ) {
                    DoseNavHost(
                        bottomBarVisibility = bottomBarVisibility,
                        fabVisibility = fabVisibility,
                        navController = navController,
                        modifier = Modifier
                            .padding(padding)
                            .consumeWindowInsets(padding)
                            .zIndex(1f)
                    )
                }
            }

            // Pass navController to the logout button
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                val context = LocalContext.current
                Surface(
                    modifier = Modifier
                        .clickable {
                            // Pass navController here
                            openLoginScreen( navController, context)
                        }
                        .align(Alignment.TopEnd)
                        .padding(6.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Text("Logout")
                }
            }
        }
    }
}

fun openLoginScreen(navController: NavController, context: Context) {
    // open login screen
    (context as MainActivity).finish()
    logout(navController, context)
}

fun logout(navController: NavController, context: Context) {
    // Clear the "isLoggedIn" preference to log out the user
    val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()

    // Navigate to the Login screen
    try {
        navController.navigate("LoginScreen")
    } catch (e: Exception) {
        Log.e("DD", e.message.toString()) //exception is here
    }
}


@Composable
private fun DoseBottomBar(
    onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    analyticsHelper: AnalyticsHelper
) {
    // Wrap the navigation bar in a surface so the color behind the system
    // navigation is equal to the container color of the navigation bar.
    Surface(color = MaterialTheme.colorScheme.surface) {
        NavigationBar(
            modifier = Modifier.windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                )
            ),
            tonalElevation = 0.dp
        ) {

            TOP_LEVEL_DESTINATIONS.forEach { destination ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == destination.route } == true
                NavigationBarItem(
                    selected = selected,
                    onClick =
                    {
                        trackTabClicked(analyticsHelper, destination.route)
                        onNavigateToTopLevelDestination(destination)
                    },
                    icon = {
                        Icon(
                            if (selected) {
                                destination.selectedIcon
                            } else {
                                destination.unselectedIcon
                            },
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(destination.iconTextId)) }
                )
            }
        }
    }
}

private fun trackTabClicked(analyticsHelper: AnalyticsHelper, route: String) {
    if (route == HomeDestination.route) {
        analyticsHelper.logEvent(AnalyticsEvents.HOME_TAB_CLICKED)
    }

    if (route == HistoryDestination.route) {
        analyticsHelper.logEvent(AnalyticsEvents.HISTORY_TAB_CLICKED)
    }
}

@Composable
fun DoseFAB(navController: NavController, analyticsHelper: AnalyticsHelper) {
    ExtendedFloatingActionButton(
        text = { Text(text = stringResource(id = R.string.add_medication)) },
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add)
            )
        },
        onClick = {
            analyticsHelper.logEvent(AnalyticsEvents.ADD_MEDICATION_CLICKED_FAB)
            navController.navigate(AddMedicationDestination.route)
        },
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
        containerColor = MaterialTheme.colorScheme.tertiary
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current
    MedicineReminderTheme {
        MedicineReminder(analyticsHelper = AnalyticsHelper(context = context))
    }
}
