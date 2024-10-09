package com.example.charma

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.charma.maincontent.MainContent
import com.example.charma.popup.RegisterPopup
import com.example.charma.splashscreen.LoginScreen
import com.example.charma.ui.theme.CharmaTheme

class MainActivity : ComponentActivity() {
    //Firebase Authentication and Database references
    private lateinit var auth: FirebaseAuth
    private lateinit var  database: DatabaseReference
    private var isLoggedIn by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = FirebaseAuth.getInstance() // Initialize Firebase Auth
        database = FirebaseDatabase.getInstance().reference // Initialize Firebase database

        setContent {
            CharmaTheme {
                //var isLoggedIn by remember { mutableStateOf(false) }
                var showRegisterPopup by remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (isLoggedIn) {
                        MainContent(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        //Pass loginUser to LoginScreen
                        LoginScreen(
                            onLoginSuccess = { email, password -> loginUser(email, password) },
                            onRegisterClick = { showRegisterPopup = true},
                            createAccount = { email, password -> createAccount(email, password)} //Pass the createAccount function
                        )

                    }
                }
                if (showRegisterPopup) {
                    RegisterPopup( onDismissRequest = { showRegisterPopup = false},
                        onCreateAccount = {email, password ->
                            createAccount(email, password)
                        })
                }
            }
        }
    }
    //This function handles the user login
    private fun loginUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI
                    val user: FirebaseUser? = auth.currentUser
                    isLoggedIn = true
                } else {
                    //shows authentication failed message
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    //This function creates new account using Firebase
    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //registration successful, update UI
                    val user: FirebaseUser? = auth.currentUser
                } else {
                    //handles registration failure
                    Toast.makeText(this, "Account Creation Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

