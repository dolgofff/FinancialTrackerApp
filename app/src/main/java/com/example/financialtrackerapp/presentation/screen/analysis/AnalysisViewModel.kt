package com.example.financialtrackerapp.presentation.screen.analysis

import android.util.Log
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtrackerapp.domain.model.Transaction
import com.example.financialtrackerapp.domain.model.enums.TransactionType
import com.example.financialtrackerapp.domain.usecase.account.GetCurrentAccountUseCase
import com.example.financialtrackerapp.domain.usecase.transactions.GetAllTransactionsUseCase
import com.example.financialtrackerapp.presentation.ui.components.ChartType
import com.example.financialtrackerapp.presentation.ui.components.categoryColorMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase
) : ViewModel() {
    private val _analysisState = MutableStateFlow(AnalysisState())
    val analysisState = _analysisState.asStateFlow()

    init {
        loadTransactionsInfo()
    }

    private fun loadTransactionsInfo() {
        viewModelScope.launch {
            try {
                val currentAccount = getCurrentAccountUseCase()
                currentAccount?.let { account ->
                    getAllTransactionsUseCase(account.id).collect { transactions ->
                        _analysisState.update {
                            val updated = it.copy(transactionList = transactions)
                            updated.copy(
                                categorySums = calculateCategorySums(
                                    transactions,
                                    updated.selectedType
                                ),
                                colors = calculateColors(transactions, updated.selectedType)
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("AnalysisViewModel", "Error loading transactions", e)
            }
        }
    }

    fun toggleTransactionType() {
        _analysisState.update {
            val newType = if (it.selectedType == TransactionType.EXPENSE)
                TransactionType.INCOME
            else
                TransactionType.EXPENSE

            it.copy(
                selectedType = newType,
                categorySums = calculateCategorySums(it.transactionList, newType),
                colors = calculateColors(it.transactionList, newType)
            )
        }
    }

    fun setChartType(type: ChartType) {
        _analysisState.update { it.copy(selectedChartType = type) }
    }

    private fun calculateCategorySums(
        transactions: List<Transaction>,
        type: TransactionType
    ): Map<String, Float> {
        return transactions
            .filter { it.type == type }
            .groupBy { it.category }
            .mapValues { it.value.sumOf { t -> t.amount }.toFloat() }
            .mapKeys {
                it.key.name.replace("_", " ")
                    .lowercase()
                    .replaceFirstChar { c -> c.uppercase() }
            }
    }

    private fun calculateColors(
        transactions: List<Transaction>,
        type: TransactionType
    ): List<Int> {
        val categories = transactions
            .filter { it.type == type }
            .map { it.category }
            .distinct()

        return categories.map { categoryColorMap[it] ?: Gray }.map { it.toArgb() }
    }

    data class AnalysisState(
        val transactionList: List<Transaction> = emptyList(),
        val selectedType: TransactionType = TransactionType.EXPENSE,
        val selectedChartType: ChartType = ChartType.PIE,
        val categorySums: Map<String, Float> = emptyMap(),
        val colors: List<Int> = emptyList(),
    )
}
