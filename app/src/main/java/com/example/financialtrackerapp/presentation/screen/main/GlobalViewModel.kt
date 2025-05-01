package com.example.financialtrackerapp.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.usecase.account.GetCurrentAccountUseCase
import com.example.financialtrackerapp.domain.usecase.account.GetUsersAccountsUseCase
import com.example.financialtrackerapp.domain.usecase.account.SaveCurrentAccountIdUseCase
import com.example.financialtrackerapp.domain.usecase.security.InitializeUserUseCase
import com.example.financialtrackerapp.domain.usecase.security.SplashUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val splashUseCase: SplashUseCase,
    private val initializeUserUseCase: InitializeUserUseCase,
    private val getUsersAccountsUseCase: GetUsersAccountsUseCase,
    private val saveCurrentAccountIdUseCase: SaveCurrentAccountIdUseCase,
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase,
) : ViewModel() {
    private val _isAuthenticated = MutableStateFlow<Boolean?>(null)
    val isAuthenticated: StateFlow<Boolean?> = _isAuthenticated

    private val _globalState = MutableStateFlow(GlobalState())
    val globalState = _globalState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _isAuthenticated.update { splashUseCase() }
        }
    }

    private fun updateUsername(username: String) {
        _globalState.update { it.copy(username = username) }
    }

    private fun updateAccountList(accountList: Flow<List<Account>>) {
        _globalState.update { it.copy(accountList = accountList) }
    }

    fun updateCurrentAccount(newAccount: Account?) {
        viewModelScope.launch {
            _globalState.update { it.copy(currentAccount = newAccount) }
            saveCurrentAccountIdUseCase(currentAccountId = newAccount?.id ?: 0L)
        }
    }

    fun loadUserData() {
        viewModelScope.launch {
            if (isAuthenticated.value == true) {
                updateUsername(username = initializeUserUseCase())
                updateCurrentAccount(newAccount = getCurrentAccountUseCase())
                updateAccountList(accountList = getUsersAccountsUseCase(_globalState.value.username))
            }
        }
    }

    data class GlobalState(
        val username: String = "",
        val accountList: Flow<List<Account>> = flowOf(emptyList()),
        val currentAccount: Account? = null
    )

}