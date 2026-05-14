package com.example.womensafetyapp.util

import android.content.Context
import android.content.Intent
import com.example.womensafetyapp.Auth.AuthActivity
import com.example.womensafetyapp.data.TokenManager

fun logoutAndReturnToAuth(context: Context, tokenManager: TokenManager) {
    tokenManager.clearSession()
    val intent = Intent(context, AuthActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    context.startActivity(intent)
}
