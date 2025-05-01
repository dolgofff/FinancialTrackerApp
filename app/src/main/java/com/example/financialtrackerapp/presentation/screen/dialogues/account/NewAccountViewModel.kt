package com.example.financialtrackerapp.presentation.screen.dialogues.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtrackerapp.domain.model.enums.AccountType
import com.example.financialtrackerapp.domain.model.enums.Currency
import com.example.financialtrackerapp.domain.usecase.account.CreateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewAccountViewModel @Inject constructor(
    private val createAccountUseCase: CreateAccountUseCase
) : ViewModel() {

    private val _accountState = MutableStateFlow(AccountState())
    val accountState: StateFlow<AccountState> = _accountState

    fun resetState() {
        _accountState.value = AccountState()
    }

    fun updateName(inputText: String) {
        _accountState.update { it.copy(name = inputText) }
    }

    fun updateType(inputType: AccountType) {
        _accountState.update { it.copy(type = inputType) }
    }

    fun updateCurrency(inputCurrency: Currency) {
        _accountState.update { it.copy(currency = inputCurrency) }
    }

    fun updateInitialBalance(inputBalance: Double) {
        _accountState.update { it.copy(initialBalance = inputBalance) }
    }

    fun createNewAccount() {
        viewModelScope.launch {
            if (validateAccountName(_accountState.value.name)) {
                return@launch
            }

            val isCreated = createAccountUseCase(
                name = _accountState.value.name,
                type = _accountState.value.type,
                currency = _accountState.value.currency,
                balance = _accountState.value.initialBalance
            )

            if (isCreated) {
                _accountState.update { it.copy(isCreated = true, errorMessage = null) }
            } else {
                updateName("")
            }
        }
    }

    private fun validateAccountName(name: String): Boolean {
        when {
            name.isBlank() -> {
                _accountState.update {
                    it.copy(
                        errorType = ErrorType.EMPTY_ACCOUNT_NAME_STATE,
                        errorMessage = "Incorrect account name."
                    )
                }
                return true
            }

            else -> {
                _accountState.update {
                    it.copy(
                        errorType = ErrorType.CORRECT_ACCOUNT_NAME_STATE,
                        errorMessage = null
                    )
                }
                return false
            }
        }

    }

    data class AccountState(
        val name: String = "",
        val type: AccountType = AccountType.SINGLE,
        val currency: Currency = Currency.USD,
        val initialBalance: Double = 0.0,
        val errorType: ErrorType = ErrorType.EMPTY_ACCOUNT_NAME_STATE,
        val errorMessage: String? = null,
        val isCreated: Boolean = false,
    )

    enum class ErrorType {
        EMPTY_ACCOUNT_NAME_STATE,
        CORRECT_ACCOUNT_NAME_STATE,
    }
}