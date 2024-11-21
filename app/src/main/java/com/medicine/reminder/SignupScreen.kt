package com.medicine.reminder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SignUpScreen(
    onSignUpComplete: () -> Unit,
    onLogin: () -> Unit // Callback to navigate back to login
) {
    // Remember states for user input
    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Welcome Text
            Text("Create a New Account")

            // Username Field
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Username") },
                modifier = Modifier.padding(top = 16.dp)
            )

            // Email Field
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                modifier = Modifier.padding(top = 16.dp)
            )

            // Password Field
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.padding(top = 16.dp)
            )

            // Sign Up Button
            Button(
                onClick = {
                    // Placeholder for signup logic
                    if (username.value.isNotBlank() && email.value.isNotBlank() && password.value.isNotBlank()) {
                        onSignUpComplete()
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Sign Up")
            }

            // Navigate to Login
            Button(
                onClick = { onLogin() },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Back to Login")
            }
        }
    }
}
