package com.example.womensafetyapp.Auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.womensafetyapp.MainActivity
import com.example.womensafetyapp.R
import com.example.womensafetyapp.ui.theme.WomenSafetyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.womensafetyapp.viewmodel.AuthViewModel
import com.example.womensafetyapp.data.LoginRequest
import com.example.womensafetyapp.data.RegisterRequest
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WomenSafetyAppTheme {
                AuthScreen(
                    onLoginSuccess = {
                        // Navigate to MainActivity
                        startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                        finish() // Close AuthActivity so user can't go back
                    }
                )
            }
        }
    }
}

@Composable
fun AuthScreen(
    onLoginSuccess: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var isLogin by remember { mutableStateOf(true) }
    
    val isLoading by authViewModel.isLoading.collectAsState()
    val error by authViewModel.error.collectAsState()
    val authSuccess by authViewModel.authSuccess.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authSuccess) {
        if (authSuccess) {
            if (isLogin) {
                authViewModel.resetSuccess()
                onLoginSuccess()
            } else {
                Toast.makeText(context, "Registration successful! Please login.", Toast.LENGTH_LONG).show()
                authViewModel.resetSuccess()
                isLogin = true // Switch to login screen
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFF6B9D), // Bright pink
                        Color(0xFFC44CE6), // Bright purple
                        Color(0xFF6B73FF)  // Bright blue
                    )
                )
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Header Section
        GradientHeader()

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 Auth Form (Login or Signup)
        if (isLogin) {
            LoginForm(
                onSwitchToSignup = { isLogin = false },
                authViewModel = authViewModel,
                isLoading = isLoading,
                apiError = error
            )
        } else {
            SignUpForm(
                onSwitchToLogin = { isLogin = true },
                authViewModel = authViewModel,
                isLoading = isLoading,
                apiError = error
            )
        }
        
        // Add bottom padding to ensure content is not cut off
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun GradientHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFE0BBE4), // startColor
                        Color(0xFFFFC1E3)  // endColor
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY) // vertical gradient
                ),
                shape = RoundedCornerShape(
                    bottomStart = 24.dp,
                    bottomEnd = 24.dp
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally // center children in Column
    ) {
        // Gradient text for "SafeGuard"
        Text(
            text = "SafeGuard",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium.copy(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF8E2DE2), Color(0xFFDA22FF)) // Purple → Pink gradient
                )
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth() // make Text take full width
                .padding(top = 24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle
        Text(
            text = "Your personal safety companion. Stay protected, stay connected, stay safe.",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
    }
}


@Composable
fun LoginForm(
    onSwitchToSignup: () -> Unit,
    authViewModel: AuthViewModel,
    isLoading: Boolean,
    apiError: String?
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf("") }
    
    val displayError = if (localError.isNotEmpty()) localError else apiError ?: ""

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(0.9f),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("SafeGuard", fontSize = 28.sp, color = Color(0xFFFF6B9D), fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            Text("Welcome Back", style = MaterialTheme.typography.titleLarge)

            if (displayError.isNotEmpty()) {
                Spacer(Modifier.height(6.dp))
                Text(displayError, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF6B9D),
                    unfocusedBorderColor = Color(0xFFC44CE6).copy(alpha = 0.5f)
                )
            )

            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFF6B9D), // Bright pink
                                Color(0xFFC44CE6), // Bright purple
                                Color(0xFF6B73FF)  // Bright blue
                            )
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Button(
                    onClick = {
                        if (username.isBlank() || password.isBlank()) {
                            localError = "Please fill in all fields"
                        } else {
                            localError = ""
                            authViewModel.login(LoginRequest(username = username, password = password))
                        }
                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent // Important! Transparent so gradient shows
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            text = "Login",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(6.dp))
            Text(
                text = "Don't have an account? Sign Up",
                color = Color(0xFFFF6B9D),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onSwitchToSignup() }
            )
        }
    }
}

@Composable
fun SignUpForm(
    onSwitchToLogin: () -> Unit,
    authViewModel: AuthViewModel,
    isLoading: Boolean,
    apiError: String?
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf("") }
    
    val displayError = if (localError.isNotEmpty()) localError else apiError ?: ""

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(0.9f),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("SafeGuard", fontSize = 28.sp, color = Color(0xFFFF6B9D), fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Text("Create Account", style = MaterialTheme.typography.titleLarge)

            if (displayError.isNotEmpty()) {
                Spacer(Modifier.height(6.dp))
                Text(displayError, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF6B9D),
                    unfocusedBorderColor = Color(0xFFC44CE6).copy(alpha = 0.5f)
                )
            )

            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF6B9D),
                    unfocusedBorderColor = Color(0xFFC44CE6).copy(alpha = 0.5f)
                )
            )

            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF6B9D),
                    unfocusedBorderColor = Color(0xFFC44CE6).copy(alpha = 0.5f)
                )
            )

            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFF6B9D), // Bright pink
                                Color(0xFFC44CE6), // Bright purple
                                Color(0xFF6B73FF)  // Bright blue
                            )
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Button(
                    onClick = {
                        if (username.isBlank() || password.isBlank() || email.isBlank() || phone.isBlank()) {
                            localError = "Please fill in all fields"
                        } else {
                            localError = ""
                            authViewModel.register(RegisterRequest(
                                username = username,
                                email = email,
                                password = password,
                                phoneNumber = phone
                            ))
                        }
                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent // Important! Transparent so gradient shows
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            text = "Create Account",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            Text(
                text = "Already have an account?",
                color = Color(0xFFFF6B9D),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.clickable { onSwitchToLogin() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthPreview() {
    WomenSafetyAppTheme {
        AuthScreen(onLoginSuccess = { /* Preview - no action */ })
    }
}
