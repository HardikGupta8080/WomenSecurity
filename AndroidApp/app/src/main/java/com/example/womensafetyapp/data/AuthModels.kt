package com.example.womensafetyapp.data

data class LoginRequest(
    val username: String,
    val password: String
)

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String,
    val phoneNumber: String
)

data class UserProfile(
    val username: String,
    val email: String,
    val phoneNumber: String
)

data class AuthResponse(
    val token: String?,
    val message: String?,
    val error: String?,
    val username: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null
)
