package com.example.womensafetyapp.data

import com.example.womensafetyapp.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {

    suspend fun login(request: LoginRequest): Result<AuthResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(request)
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    val token = body.token
                    if (token != null) {
                        tokenManager.saveToken(token)
                        tokenManager.saveUserProfile(
                            username = body.username ?: request.username,
                            email = body.email.orEmpty(),
                            phoneNumber = body.phoneNumber.orEmpty()
                        )
                        Result.success(body.copy(message = body.message ?: "Login successful", error = null))
                    } else {
                        Result.failure(Exception("Token missing from response"))
                    }
                } else {
                    Result.failure(Exception("Login failed: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun register(request: RegisterRequest): Result<AuthResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.register(request)
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    tokenManager.saveUserProfile(
                        username = request.username,
                        email = request.email,
                        phoneNumber = request.phoneNumber
                    )
                    Result.success(body.copy(message = body.message ?: "Registration successful", error = null))
                } else {
                    Result.failure(Exception("Registration failed: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
