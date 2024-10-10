package com.example.charma.popup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.charma.R
import com.example.charma.ui.theme.NinerGold
import com.example.charma.ui.theme.QuartzWhite
import com.example.charma.ui.theme.UNCCGreen

@Composable
fun RegisterPopup(onDismissRequest: () -> Unit, onCreateAccount: (String, String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}
    Dialog(onDismissRequest = onDismissRequest) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            val image = painterResource(id = R.drawable.sign)

            // Create image box
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(QuartzWhite)
            )
            {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop, //Crop image to fit screen
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.2f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Please enter your account email."
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Please enter your password")

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "By selecting Create account, you agree to our User Agreement",
                        fontSize = 10.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            // Register Account Login
                            onCreateAccount(email, password) //call the callback
                            onDismissRequest() //closes popup
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = UNCCGreen
                        )
                    ) {
                        Text(text = "Create Account")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            // Back to Login
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NinerGold
                        )
                    )
                    {
                        Text(text = "Back to Log In")
                    }

                }
            }
        }
    }
}

@Composable
fun ForgotPassword(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        var email by remember { mutableStateOf("") }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            val image = painterResource(id = R.drawable.sign)

            // Create image box
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(QuartzWhite)
            )
            {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop, //Crop image to fit screen
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.2f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Please enter your account email."
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            // Back to Login
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = UNCCGreen
                        )
                    )
                    {
                        Text(text = "Send reset password email")
                    }

                }
            }
        }
    }
}
