APP NAME : "MEDICINE REMINDER"
Author : Flores, Michael 

Description and Working of the Medicine Reminder Application :
"The Medicine Reminder application is designed as an Android app using Jetpack Compose, following a 
modular and reusable architecture. It features a login screen, a navigation-based UI for managing 
reminders, and integration with analytics. Below is a detailed breakdown of its components and 
functionality:
Main Features
1. Login Screen:
o Provides fields for entering a username and password.
o Features a Login button to validate credentials and redirect users upon successful 
authentication.
o Includes a Sign Up button for navigating to a registration screen.
2. Navigation:
o The app uses NavController for handling navigation between screens, including home, 
history, and add-medication screens.
o The DoseNavHost manages navigation between these destinations.
3. Floating Action Button (FAB):
o A FAB allows users to quickly add new medications.
o The FAB visibility is dynamically managed and animated using Jetpack Compose's 
AnimatedVisibility.
4. Bottom Navigation Bar:
o A persistent navigation bar displays the top-level destinations like Home and History.
o Icons and labels are dynamically updated based on the user's navigation state.
5. Analytics Integration:
o Tracks user interactions like switching tabs or clicking the FAB.
o Logs events using an AnalyticsHelper object for monitoring app usage patterns.
6. Logout Functionality:
o Logs the user out by updating a shared preference (isLoggedIn) and navigating to the 
login screen.
7. Themes and Styling:
o The app utilizes a custom theme (MedicineReminderTheme) to maintain a consistent 
look and feel.
o Colors, padding, and other design elements are applied following Material Design 
guidelines.
Detailed Working
Login Process
1. Input Fields:
o Users input their username and password in OutlinedTextField components.
2. Validation:
o Basic validation logic checks hardcoded credentials (admin and password).
3. On Success:
o Upon successful login, the app transitions to the main screen using the onLoginSuccess
callback.
Main Screen Layout
• The Scaffold component organizes the layout:
o Floating Action Button (FAB): Animated with slide-in and slide-out effects.
o Bottom Navigation Bar: Provides tabs for navigating between key destinations.
Navigation
• A NavController tracks the current screen and manages backstack navigation.
• The DoseNavHost contains logic for switching between screens like home, history, and adding 
medication.
Logout
• The openLoginScreen function resets the isLoggedIn preference and navigates to the login 
screen using the NavController.
Analytics
• The AnalyticsHelper logs events like tab switches (trackTabClicked) and FAB clicks to track user 
behavior.
Dynamic Visibility
• AnimatedVisibility controls the appearance of the FAB and bottom bar, ensuring a polished user 
experience with smooth transitions.
Error Handling
• Errors during navigation (e.g., navController.navigate) are caught and logged using Log.e.
Preview
The DefaultPreview provides a preview of the MedicineReminder composable in Android Studio, 
enabling developers to visualize the app during development.
Usage Scenarios
• Daily Reminders: Users can set reminders for taking medication.
• Medication Tracking: Users can view their medication history and manage their reminders 
effectively.
• Custom Analytics: App owners can analyze user interactions to improve functionality and user 
experience
