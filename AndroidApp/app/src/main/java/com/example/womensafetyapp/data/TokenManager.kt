package com.example.womensafetyapp.data

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("jwt_token", null)
    }

    fun clearToken() {
        prefs.edit().remove("jwt_token").apply()
    }

    fun saveUserProfile(username: String, email: String, phoneNumber: String) {
        prefs.edit()
            .putString("username", username)
            .putString("email", email)
            .putString("phone_number", phoneNumber)
            .apply()
    }

    fun getUserProfile(): UserProfile {
        return UserProfile(
            username = prefs.getString("username", null).orEmpty(),
            email = prefs.getString("email", null).orEmpty(),
            phoneNumber = prefs.getString("phone_number", null).orEmpty()
        )
    }

    fun clearSession() {
        prefs.edit()
            .remove("jwt_token")
            .remove("username")
            .remove("email")
            .remove("phone_number")
            .apply()
    }
}
