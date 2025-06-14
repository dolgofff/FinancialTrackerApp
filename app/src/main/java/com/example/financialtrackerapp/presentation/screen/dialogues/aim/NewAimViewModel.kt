package com.example.financialtrackerapp.presentation.screen.dialogues.aim

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtrackerapp.domain.usecase.aims.CreateAimUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewAimViewModel @Inject constructor(private val createAimUseCase: CreateAimUseCase) :
    ViewModel() {
    private val _aimState = MutableStateFlow(AimState())
    val aimState = _aimState.asStateFlow()

    fun updateName(name: String) {
        _aimState.update { it.copy(name = name) }
    }

    fun updateTargetAmount(targetAmount: Double) {
        _aimState.update { it.copy(targetAmount = targetAmount) }
    }

    fun updateSavedAmount(savedAmount: Double) {
        _aimState.update { it.copy(savedAmount = savedAmount) }
    }

    fun clear() {
        _aimState.update { AimState() }
    }

    fun createAim() {
        with(_aimState.value) {
            validateAim(name, targetAmount, savedAmount)

            if (errorMessage == null) {
                viewModelScope.launch {
                    val isCreated = createAimUseCase(
                        name = name,
                        targetAmount = targetAmount,
                        savedAmount = savedAmount
                    )

                    if (isCreated) {
                        _aimState.update { it.copy(isCreated = true) }
                    }
                }
            }
        }
    }

    private fun validateAim(name: String, targetAmount: Double, savedAmount: Double) {
        val errors = mutableListOf<ErrorType>()

        if (name.isBlank()) errors.add(ErrorType.EMPTY_NAME_ERROR)
        if (targetAmount == 0.0) errors.add(ErrorType.EMPTY_TARGET_AMOUNT_ERROR)
        if (savedAmount == 0.0) errors.add(ErrorType.EMPTY_SAVED_AMOUNT_ERROR)
        if (targetAmount <= savedAmount) errors.add(ErrorType.INCORRECT_AMOUNT_RANGE)

        if (errors.isEmpty()) {
            _aimState.update { it.copy(errorMessage = null) }
            return
        }

        val (message, type) = when {
            errors.size > 1 -> "Please, fill in all fields." to ErrorType.SEVERAL_EMPTY_FIELDS_ERROR
            errors.contains(ErrorType.INCORRECT_AMOUNT_RANGE) -> "Enter the correct range." to ErrorType.INCORRECT_AMOUNT_RANGE
            errors.contains(ErrorType.EMPTY_NAME_ERROR) -> "Enter the name." to ErrorType.EMPTY_NAME_ERROR
            errors.contains(ErrorType.EMPTY_TARGET_AMOUNT_ERROR) -> "Enter target amount." to ErrorType.EMPTY_TARGET_AMOUNT_ERROR
            errors.contains(ErrorType.EMPTY_SAVED_AMOUNT_ERROR) -> "Enter saved amount." to ErrorType.EMPTY_SAVED_AMOUNT_ERROR
            else -> "Unknown error" to ErrorType.SEVERAL_EMPTY_FIELDS_ERROR
        }

        _aimState.update { it.copy(errorMessage = message, errorType = type) }
    }

    data class AimState(
        val name: String = "",
        val targetAmount: Double = 0.0,
        val savedAmount: Double = 0.0,
        val errorMessage: String? = null,
        val errorType: ErrorType = ErrorType.SEVERAL_EMPTY_FIELDS_ERROR,
        val isCreated: Boolean = false,
    )

    enum class ErrorType {
        EMPTY_NAME_ERROR,
        EMPTY_TARGET_AMOUNT_ERROR,
        EMPTY_SAVED_AMOUNT_ERROR,
        SEVERAL_EMPTY_FIELDS_ERROR,
        INCORRECT_AMOUNT_RANGE,
    }
}