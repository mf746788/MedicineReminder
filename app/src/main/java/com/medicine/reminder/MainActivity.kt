package com.medicine.reminder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.medicine.reminder.analytics.AnalyticsEvents
import com.medicine.reminder.analytics.AnalyticsHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isLoggedIn = isUserLoggedIn()

        setContent {
            val showSignup = remember { mutableStateOf(false) }

            if (isLoggedIn) {
                MedicineReminder(analyticsHelper = analyticsHelper)
            } else {
                if (showSignup.value) {
                    SignUpScreen(
                        onSignUpComplete = {
                            showSignup.value = false // Navigate back to login
                        },
                        onLogin = {
                            showSignup.value = false // Switch to login screen
                        }
                    )
                } else {
                    LoginScreen(
                        onLoginSuccess = { onLoginSuccess() },
                        onSignUp = { showSignup.value = true } // Navigate to signup screen
                    )
                }
            }
        }

        parseIntent(intent)
    }

    private fun parseIntent(intent: Intent?) {
        val isMedicationNotification =
            intent?.getBooleanExtra(MEDICATION_NOTIFICATION, false) ?: false
        if (isMedicationNotification) {
            analyticsHelper.logEvent(AnalyticsEvents.REMINDER_NOTIFICATION_CLICKED)
        }
    }

    private fun isUserLoggedIn(): Boolean {


        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun onLoginSuccess() {
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

        // Restart the activity to show the main content
        finish()
        startActivity(intent)
    }

}
