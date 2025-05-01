package com.example.financialtrackerapp.presentation.screen.dialogues.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.domain.model.enums.TransactionType
import com.example.financialtrackerapp.domain.usecase.account.GetCurrentAccountUseCase
import com.example.financialtrackerapp.domain.usecase.account.UpdateAccountUseCase
import com.example.financialtrackerapp.domain.usecase.transactions.CreateTransactionUseCase
import com.example.financialtrackerapp.presentation.ui.components.formatTransactionDate
import com.example.financialtrackerapp.presentation.ui.components.parseTransactionDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewTransactionViewModel @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
) :
    ViewModel() {
    private val _transactionState = MutableStateFlow(TransactionState())
    val transactionState = _transactionState.asStateFlow()

    fun updateAmount(amount: Double) {
        _transactionState.update { it.copy(amount = amount) }
    }

    fun updateCategory(category: Category) {
        _transactionState.update { it.copy(category = category) }
    }

    fun updateNote(note: String) {
        _transactionState.update { it.copy(note = note) }
    }

    fun updatePlace(place: String) {
        _transactionState.update { it.copy(place = place) }
    }

    fun updateSelectedType(type: TransactionType) {
        _transactionState.update { it.copy(selectedType = type) }
    }

    fun resetState(){
        _transactionState.value = TransactionState()
    }

    fun createNewTransaction() {
        viewModelScope.launch {
            val currentAccount = getCurrentAccountUseCase()

            currentAccount?.let { account ->
                val amount = _transactionState.value.amount

                val newBalance = when (_transactionState.value.selectedType) {
                    TransactionType.EXPENSE -> account.balance - amount
                    TransactionType.INCOME, TransactionType.TRANSFER -> account.balance + amount
                }

                updateAccountUseCase(account.copy(balance = newBalance))

                createTransactionUseCase(
                    accountId = account.id,
                    amount = _transactionState.value.amount,
                    category = _transactionState.value.category,
                    type = _transactionState.value.selectedType,
                    date = parseTransactionDate(_transactionState.value.date),
                    note = _transactionState.value.note,
                    place = _transactionState.value.place,
                )
            }
        }
    }

    data class TransactionState(
        val amount: Double = 0.0,
        val category: Category = Category.EMPTY_CATEGORY,
        val note: String = "",
        val date: String = formatTransactionDate(System.currentTimeMillis()),
        val place: String = "",
        val selectedType: TransactionType = TransactionType.EXPENSE
    )
}