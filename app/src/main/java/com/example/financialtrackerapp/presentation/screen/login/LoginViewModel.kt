package com.example.financialtrackerapp.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtrackerapp.domain.usecase.security.AuthenticationUseCase
import com.example.financialtrackerapp.domain.usecase.security.ForgottenPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,
    private val forgottenPasswordUseCase: ForgottenPasswordUseCase,
) : ViewModel() {

    private val _authState = MutableStateFlow<SignInState>(SignInState())
    val authState: StateFlow<SignInState> = _authState

    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible

    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value
    }

    fun updateUsernameText(inputText: String) {
        _authState.update { it.copy(username = inputText) }
    }

    fun updatePasswordText(inputText: String) {
        _authState.update { it.copy(password = inputText) }
    }

    private fun clearUsernameAndPasswordFields() {
        _authState.update { it.copy(username = "", password = "") }
    }

    private fun updateErrorType(errorType: ErrorType) {
        val message = when (errorType) {
            ErrorType.EMPTY_CREDENTIALS_STATE -> "Please, enter your credentials."
            ErrorType.EMPTY_USERNAME_STATE -> "Please, enter your username."
            ErrorType.EMPTY_PASSWORD_STATE -> "Please, enter your password."
            ErrorType.WRONG_CREDENTIALS_STATE -> "Incorrect password, try again."
            else -> null
        }
        _authState.update { it.copy(errorType = errorType, errorMessage = message) }
    }

    fun signIn() {
        viewModelScope.launch {
            val errorType = validateCredentials(
                username = _authState.value.username,
                password = _authState.value.password
            )

            if (errorType != null) {
                updateErrorType(errorType)
                return@launch
            }

            val isAuthenticated = authenticationUseCase(
                username = _authState.value.username,
                password = _authState.value.password
            )

            if (isAuthenticated) {
                updateErrorType(ErrorType.CORRECT_CREDENTIALS_STATE)
                _authState.update { it.copy(isAuthenticated = true, errorMessage = null) }
            } else {
                updateErrorType(ErrorType.WRONG_CREDENTIALS_STATE)
                clearUsernameAndPasswordFields()
            }
        }
    }

    private fun validateCredentials(username: String, password: String): ErrorType? = when {
        username.isBlank() && password.isBlank() -> ErrorType.EMPTY_CREDENTIALS_STATE
        username.isBlank() -> ErrorType.EMPTY_USERNAME_STATE
        password.isBlank() -> ErrorType.EMPTY_PASSWORD_STATE
        else -> null
    }

    data class SignInState(
        val username: String = "",
        val password: String = "",
        val errorType: ErrorType = ErrorType.EMPTY_CREDENTIALS_STATE,
        val errorMessage: String? = null,
        val isAuthenticated: Boolean = false,
    )

    enum class ErrorType {
        CORRECT_CREDENTIALS_STATE,
        WRONG_CREDENTIALS_STATE,
        EMPTY_CREDENTIALS_STATE,
        EMPTY_USERNAME_STATE,
        EMPTY_PASSWORD_STATE,
    }
}