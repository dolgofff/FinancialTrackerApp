package com.example.financialtrackerapp.presentation.screen.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtrackerapp.domain.model.Transaction
import com.example.financialtrackerapp.domain.model.enums.TransactionType
import com.example.financialtrackerapp.domain.usecase.account.GetCurrentAccountUseCase
import com.example.financialtrackerapp.domain.usecase.transactions.GetAllTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase,
) : ViewModel() {

    private val _transactionsState = MutableStateFlow(FinancialState())
    val transactionsState = _transactionsState.asStateFlow()

    init {
        loadTransactionsInfo()
    }

    private fun loadTransactionsInfo() {
        viewModelScope.launch {
            val currentAccount = getCurrentAccountUseCase()
            currentAccount?.let {
                getAllTransactionsUseCase(it.id).collect { transactions ->
                    _transactionsState.update { state ->
                        state.copy(
                            transactionList = transactions,
                            incomeValue = calculateIncomeValue(transactions),
                            expenseValue = calculateExpenseValue(transactions)
                        )
                    }
                }
            }
        }
    }

    private fun calculateIncomeValue(transactions: List<Transaction>): Double {
        return transactions.filter {
            (it.type == TransactionType.INCOME) || (it.type == TransactionType.TRANSFER && it.amount > 0)
        }.sumOf { it.amount }
    }

    private fun calculateExpenseValue(transactions: List<Transaction>): Double {
        return transactions.filter {
            (it.type == TransactionType.EXPENSE) || (it.type == TransactionType.TRANSFER && it.amount < 0)
        }.sumOf { it.amount }.let { -it }
    }

    data class FinancialState(
        val incomeValue: Double = 0.0,
        val expenseValue: Double = 0.0,
        val transactionList: List<Transaction> = emptyList()
    )
}
