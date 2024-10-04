package com.example.charma

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.charma.popup.ForgotPassword
import com.example.charma.popup.RegisterPopup
import com.example.charma.ui.theme.CharmaTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

// UNCC's green color in hex
val UNCCGreen = Color(0xFF006A4D) // Example value, adjust to the exact color
val NinerGold = Color(0xFFA49665)
val QuartzWhite = Color(0xFFFFFFFF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CharmaTheme {
                var isLoggedIn by remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (isLoggedIn) {
                        MainContent(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        LoginScreen(onLoginSuccess = { isLoggedIn = true })
                    }
                }
            }
        }
    }
}


// region Login
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
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
        var showForgotPasswordPopup by remember { mutableStateOf(false)}

        Column(
            modifier = Modifier
                .fillMaxSize()
                // Create space between content of a view and its borders
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image (
                    painter = painterResource(id = R.drawable.uncclogo),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "HARMA", Modifier
                    .scale(2f))
            }

            Spacer(modifier = Modifier.height(24.dp))

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
                        onLoginSuccess()
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

        if (showRegisterPopup) {
            RegisterPopup (onDismissRequest = { showRegisterPopup = false})
        }
        if (showForgotPasswordPopup) {
            ForgotPassword (onDismissRequest = { showForgotPasswordPopup = false})
        }
    }
}


// endregion

@Composable
fun MainContent(name: String, modifier: Modifier = Modifier)
{

    // Set initial camera position for Google Maps
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(35.3076, -80.7351), 16f)
    }

    // Context to be used for dialing
    val context = LocalContext.current

    // State to toggle emergency options visibility
    var showEmergencyOptions by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize())
    {
        // Google Map Composable
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        )

        // UI elements overlaid on top of the map
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        )
        {
            Spacer(modifier = Modifier.weight(1f))

            // Emergency Options
            if (showEmergencyOptions)
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 4.dp, bottom = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    // 911 Button
                    Button(
                        onClick =
                        {
                            // Intent to call 911
                            val callIntent = Intent(Intent.ACTION_DIAL)
                            callIntent.data = Uri.parse("tel:911")
                            context.startActivity(callIntent)
                        },
                        modifier = Modifier.fillMaxWidth(),

                        // Set to red for 911
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        )
                    )
                    {
                        Text(text = "Call 911")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Campus Police Button
                    Button(
                        onClick =
                        {
                            // Intent to call campus police
                            val callIntent = Intent(Intent.ACTION_DIAL)
                            callIntent.data = Uri.parse("tel:7046872200")
                            context.startActivity(callIntent)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = UNCCGreen
                        )
                    )
                    {
                        Text(text = "Call Campus Police")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Main action buttons (Search, Favorites, Emergencies)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* TODO: Handle Search action */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = UNCCGreen // Set to UNCC Green
                    )
                ) {
                    Text(text = "Search")
                }

                Button(
                    onClick = { /* TODO: Handle Favorites action */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = UNCCGreen
                    )
                ) {
                    Text(text = "Favorites")
                }

                Button(
                    onClick = {
                        // Toggle visibility of emergency options
                        showEmergencyOptions = !showEmergencyOptions
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text(text = "Emergencies")
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.uncclogo),
            contentDescription = "UNCC Logo",
            modifier = Modifier
                .padding(16.dp)
                .size(64.dp)
                .align(Alignment.TopStart)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    CharmaTheme {
        MainContent("Android")
    }
}
