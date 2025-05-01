package com.example.financialtrackerapp.presentation.screen.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtrackerapp.domain.usecase.security.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
) : ViewModel() {
    private val _registrationState = MutableStateFlow(RegisterState())
    val registrationState: StateFlow<RegisterState> = _registrationState

    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible

    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value
    }

    fun updateUsernameText(inputText: String) {
        _registrationState.update { it.copy(username = inputText) }
    }

    fun updatePasswordText(inputText: String) {
        _registrationState.update { it.copy(password = inputText) }
    }

    fun updateConfirmPasswordText(inputText: String) {
        _registrationState.update { it.copy(confirmPassword = inputText) }
    }

    private fun clearUsernameAndPasswordsFields() {
        _registrationState.update { it.copy(username = "", password = "", confirmPassword = "") }
    }

    private fun updateErrorType(errorType: ErrorType) {
        val message = when (errorType) {
            ErrorType.EMPTY_CREDENTIALS_STATE -> "Please, enter your credentials."
            ErrorType.DIFFERENT_PASSWORDS_STATE -> "Your passwords doesn't match."
            ErrorType.EXISTING_USERNAME_STATE -> "Such username already exists."
            else -> null
        }
        _registrationState.update { it.copy(errorType = errorType, errorMessage = message) }
    }

    fun register() {
        viewModelScope.launch {
            val errorType = validateCredentials(
                username = _registrationState.value.username,
                password = _registrationState.value.password,
                confirmPassword = _registrationState.value.confirmPassword
            )

            if (errorType != null) {
                updateErrorType(errorType)
                return@launch
            }

            val isSuccessfullyRegistered = registrationUseCase(
                username = _registrationState.value.username,
                password = _registrationState.value.password,
                passwordHint = ""
            )

            if (isSuccessfullyRegistered) {
                updateErrorType(ErrorType.CORRECT_CREDENTIALS_STATE)
                _registrationState.update { it.copy(isRegistered = true, errorMessage = null) }
            } else {
                clearUsernameAndPasswordsFields()
                updateErrorType(ErrorType.EXISTING_USERNAME_STATE)
            }

        }
    }

    private fun validateCredentials(
        username: String,
        password: String,
        confirmPassword: String
    ): ErrorType? = when {
        username.isBlank() && password.isBlank() && confirmPassword.isBlank() -> ErrorType.EMPTY_CREDENTIALS_STATE
        (confirmPassword != password) -> ErrorType.DIFFERENT_PASSWORDS_STATE
        else -> null
    }
}

data class RegisterState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorType: ErrorType = ErrorType.EMPTY_CREDENTIALS_STATE,
    val errorMessage: String? = null,
    val isRegistered: Boolean = false,
)

enum class ErrorType {
    CORRECT_CREDENTIALS_STATE,
    EMPTY_CREDENTIALS_STATE,
    DIFFERENT_PASSWORDS_STATE,
    EXISTING_USERNAME_STATE
}

