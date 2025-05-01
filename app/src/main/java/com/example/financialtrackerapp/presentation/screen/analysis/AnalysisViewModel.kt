package com.example.financialtrackerapp.presentation.screen.analysis

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.example.financialtrackerapp.domain.model.Transaction
import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.domain.model.enums.TransactionType
import com.example.financialtrackerapp.domain.usecase.transactions.GetAllTransactionsUseCase
import com.example.financialtrackerapp.presentation.ui.components.ChartType
import com.example.financialtrackerapp.presentation.ui.components.categoryColorMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(private val getAllTransactionsUseCase: GetAllTransactionsUseCase) :
    ViewModel() {
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions = _transactions.asStateFlow()

    private val _selectedType = MutableStateFlow(TransactionType.EXPENSE)
    val selectedType = _selectedType.asStateFlow()

    fun switchType() {
        _selectedType.value = if (_selectedType.value == TransactionType.EXPENSE)
            TransactionType.INCOME
        else
            TransactionType.EXPENSE
    }

    init {
        _transactions.value = listOf(
            Transaction(2, 101, 123.15, Category.TAXI, TransactionType.EXPENSE),
            Transaction(5, 101, 432.1, Category.MEDICINE, TransactionType.EXPENSE),
            Transaction(6, 101, 91.89, Category.DEVICES, TransactionType.EXPENSE),
            Transaction(7, 101, 45.56, Category.SUBSCRIPTIONS, TransactionType.EXPENSE),
            Transaction(8, 101, 321.48, Category.ENTERTAINMENTS, TransactionType.EXPENSE),
            Transaction(9, 101, 666.11, Category.CHARITY, TransactionType.EXPENSE),
            Transaction(3, 101, 1500.0, Category.SALARY, TransactionType.INCOME),
            Transaction(4, 101, 5334.0, Category.GIFTED_INCOME, TransactionType.INCOME),
            Transaction(4, 101, 5334.0, Category.INVESTMENTS_INCOME, TransactionType.INCOME)
        )
    }

    fun getCategorySums(): Pair<Map<String, Float>, List<Int>> {
        val type = _selectedType.value
        val filtered = transactions.value.filter { it.type == type }

        val grouped = filtered
            .groupBy { it.category }
            .mapValues { it.value.sumOf { t -> t.amount }.toFloat() }

        val dataMap = grouped.mapKeys {
            it.key.name.replace("_", " ")
                .lowercase()
                .replaceFirstChar { c -> c.uppercase() }
        }

        val colors = grouped.keys.map { categoryColorMap[it] ?: Gray }.map { it.toArgb() }

        return Pair(dataMap, colors)
    }

    private val _selectedChartType = mutableStateOf(ChartType.PIE) // Default to Pie Chart
    val selectedChartType: State<ChartType> get() = _selectedChartType

    fun switchType(type: ChartType) {
        _selectedChartType.value = type
    }

}
