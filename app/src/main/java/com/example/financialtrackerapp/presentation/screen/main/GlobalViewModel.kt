package com.example.financialtrackerapp.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.usecase.account.GetAccountByIdUseCase
import com.example.financialtrackerapp.domain.usecase.account.GetCurrentAccountUseCase
import com.example.financialtrackerapp.domain.usecase.account.GetUsersAccountsUseCase
import com.example.financialtrackerapp.domain.usecase.account.SaveCurrentAccountIdUseCase
import com.example.financialtrackerapp.domain.usecase.security.InitializeUserUseCase
import com.example.financialtrackerapp.domain.usecase.security.SplashUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
) : ViewModel() {

    private val _isAuthenticated = MutableStateFlow<Boolean?>(null)
    val isAuthenticated: StateFlow<Boolean?> = _isAuthenticated

    private val _globalState = MutableStateFlow(GlobalState())
    val globalState = _globalState.asStateFlow()

    init {
        viewModelScope.launch {
            _isAuthenticated.update { splashUseCase() }
        }
    }

    fun updateCurrentAccount(newAccount: Account?) {
        viewModelScope.launch {
            _globalState.update { it.copy(currentAccount = newAccount) }
            saveCurrentAccountIdUseCase(currentAccountId = newAccount?.id ?: 0L)
        }
    }

    fun updateCurrentAccount(accountId: Long) {
        viewModelScope.launch {
            _globalState.update { it.copy(currentAccount = getAccountByIdUseCase(accountId)) }
            saveCurrentAccountIdUseCase(currentAccountId = accountId)
        }
    }

    fun setAuthenticated(authenticated: Boolean) {
        _isAuthenticated.value = authenticated
    }

    fun loadUserData() {
        viewModelScope.launch {
            val username = initializeUserUseCase()
            val currentAccount = getCurrentAccountUseCase()

            _globalState.update { it.copy(username = username, currentAccount = currentAccount) }
            getUsersAccountsUseCase(username).collect { accountList ->
                _globalState.update { old ->
                    if (old.accountList != accountList) {
                        old.copy(accountList = accountList)
                    } else {
                        old
                    }
                }
            }
        }
    }

    data class GlobalState(
        val username: String = "",
        val accountList: List<Account> = emptyList(),
        val currentAccount: Account? = null
    )
}