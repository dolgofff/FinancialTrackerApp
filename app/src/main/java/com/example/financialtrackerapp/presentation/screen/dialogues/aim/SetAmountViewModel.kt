package com.example.financialtrackerapp.presentation.screen.dialogues.aim

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtrackerapp.domain.model.Aim
import com.example.financialtrackerapp.domain.usecase.aims.UpdateAimUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetAmountViewModel @Inject constructor(private val updateAimUseCase: UpdateAimUseCase) :
    ViewModel() {
    private val _setState = MutableStateFlow(SetAmountState())
    val setState = _setState.asStateFlow()

    fun clear() {
        _setState.update { SetAmountState() }
    }

    fun updateSavedAmount(savedAmount: Double) {
        _setState.update { it.copy(savedAmount = savedAmount) }
    }

    fun updateAim(aim: Aim) {
        viewModelScope.launch {
            val updatedAim = aim.copy(savedAmount = aim.savedAmount + _setState.value.savedAmount)
            val isDone = updateAimUseCase(updatedAim)

            if (isDone) {
                _setState.update { it.copy(isDone = true) }
            }
        }
    }

    data class SetAmountState(
        val savedAmount: Double = 0.0,
        val isDone: Boolean = false,
    )
}