package com.example.charma.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.charma.R
import com.example.charma.popup.ForgotPassword
import com.example.charma.popup.RegisterPopup
import com.example.charma.ui.theme.NinerGold
import com.example.charma.ui.theme.QuartzWhite
import com.example.charma.ui.theme.UNCCGreen


// region Login
@Composable
fun LoginScreen(onLoginSuccess: (String, String) -> Unit, onRegisterClick: () -> Unit, createAccount: (String, String) -> Unit) {
    // Android requires all images to be placed inside one of the res/drawable directories (or its variants).
    val image = painterResource(id = R.drawable.temp)

    // Create image box
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(QuartzWhite)
    ) {
        // Fade the background
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop, //Crop image to fit screen
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f)
        )

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        // Popup State
        var showRegisterPopup by remember { mutableStateOf(false) }
        var showForgotPasswordPopup by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                // Create space between content of a view and its borders
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image (
                painter = painterResource(id = R.drawable.charmalogo),
                contentDescription = "",
                modifier = Modifier.size(200.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Username field
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password field
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )


            Spacer(modifier = Modifier.height(24.dp))

            // Login button
            Button(
                onClick = {
                    // Replace with actual login logic
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        onLoginSuccess(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = UNCCGreen
                )
            ) {
                Text(text = "Log In")
            }

            // Spacer
            Spacer(modifier = Modifier.height(16.dp))

            // Register button
            Button(
                onClick = {
                    // Replace with actual login logic
                    showRegisterPopup = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NinerGold
                )
            ) {
                Text(text = "Create Account")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = buildAnnotatedString {
                    append("Forgot your password?")
                },
                style = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
                modifier = Modifier.clickable {
                    showForgotPasswordPopup = true // Show popup when clicked
                }
            )

        }
        //show registration popup if triggered
        if (showRegisterPopup) {
            RegisterPopup (
                onDismissRequest = { showRegisterPopup = false},
                onCreateAccount = {email, password ->
                    createAccount(email, password)
                })
        }
        if (showForgotPasswordPopup) {
            ForgotPassword (onDismissRequest = { showForgotPasswordPopup = false})
        }
    }
}