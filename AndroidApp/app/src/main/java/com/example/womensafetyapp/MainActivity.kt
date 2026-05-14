package com.example.womensafetyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.womensafetyapp.data.ContactsViewModel
import com.example.womensafetyapp.ui.main.MainScreen
import com.example.womensafetyapp.ui.theme.WomenSafetyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Allows content to draw behind system bars

        setContent {
            WomenSafetyAppTheme {
                // Create the ViewModel using Hilt
                val contactsViewModel: ContactsViewModel = hiltViewModel()
//hi my name is hardik gupta
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        contactsViewModel = contactsViewModel
                    ) // 👈 This is your SafeGuard UI with top bar + navigation
                }
            }
        }
    }
}
