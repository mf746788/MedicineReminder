package com.medicine.reminder

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
            if (isLoggedIn) {
                MedicineReminder(analyticsHelper = analyticsHelper)
            } else {
                LoginScreen(
                    onLoginSuccess = { onLoginSuccess() },
                    onSignUp = { navigateToSignUp() }
                )
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
    private fun navigateToSignUp() {
        // Navigate to a signup activity or screen
        startActivity(Intent(this, SignUpActivity::class.java))
    }
}
