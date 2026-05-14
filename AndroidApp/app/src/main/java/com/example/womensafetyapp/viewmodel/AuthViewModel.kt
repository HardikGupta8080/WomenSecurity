package com.example.womensafetyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.womensafetyapp.data.AuthRepository
import com.example.womensafetyapp.data.LoginRequest
import com.example.womensafetyapp.data.RegisterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _authSuccess = MutableStateFlow(false)
    val authSuccess: StateFlow<Boolean> = _authSuccess.asStateFlow()

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.login(request)
            _isLoading.value = false
            
            result.onSuccess {
                _authSuccess.value = true
            }.onFailure { e ->
                _error.value = e.message ?: "An unknown error occurred"
            }
        }
    }

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.register(request)
            _isLoading.value = false
            
            result.onSuccess {
                // Registration successful, now the user should login
                // We'll set success to true, which the UI can react to (e.g. switch to login tab)
                _authSuccess.value = true
            }.onFailure { e ->
                _error.value = e.message ?: "An unknown error occurred"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun resetSuccess() {
        _authSuccess.value = false
    }
}
