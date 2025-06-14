package com.example.financialtrackerapp.presentation.screen.dialogues.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.domain.usecase.budgets.CreateBudgetUseCase
import com.example.financialtrackerapp.presentation.ui.components.formatTransactionDate
import com.example.financialtrackerapp.presentation.ui.components.parseTransactionDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewBudgetViewModel @Inject constructor(private val createBudgetUseCase: CreateBudgetUseCase) :
    ViewModel() {
    private val _budgetState = MutableStateFlow(BudgetState())
    val budgetState = _budgetState.asStateFlow()

    fun updateCategory(category: Category) {
        _budgetState.update { it.copy(category = category) }
    }

    fun updateStartDate(date: String) {
        _budgetState.update { it.copy(startDate = date) }
    }

    fun updateEndDate(date: String) {
        _budgetState.update { it.copy(endDate = date) }
    }

    fun updateSpentAmount(spentAmount: Double) {
        _budgetState.update { it.copy(spentAmount = spentAmount) }
    }

    fun updateLimitAmount(limitAmount: Double) {
        _budgetState.update { it.copy(limitAmount = limitAmount) }
    }

    fun clear() {
        _budgetState.update { BudgetState() }
    }

    fun createBudget() {
        with(_budgetState.value) {
            validateBudget(category, spentAmount, limitAmount)

            if (errorMessage == null) {
                viewModelScope.launch {
                    val isCreated = createBudgetUseCase(
                        category = category,
                        spentAmount = spentAmount,
                        limitAmount = limitAmount,
                        startDate = parseTransactionDate(startDate),
                        endDate = parseTransactionDate(endDate),
                    )

                    if (isCreated) {
                        _budgetState.update { it.copy(isCreated = true) }
                    }
                }
            }
        }
    }

    private fun validateBudget(category: Category, spentAmount: Double, limitAmount: Double) {
        val errorType: ErrorType?
        val errorMessage: String?

        val isCategoryEmpty = category == Category.EMPTY_CATEGORY
        val isSpentEmpty = spentAmount <= 0.0
        val isLimitEmpty = limitAmount <= 0.0
        val isCorrectRange = spentAmount >= limitAmount

        when {
            isCategoryEmpty && isSpentEmpty && isLimitEmpty -> {
                errorType = ErrorType.SEVERAL_EMPTY_FIELDS_ERROR
                errorMessage = "Choose category and enter amounts."
            }

            isCategoryEmpty && isSpentEmpty -> {
                errorType = ErrorType.SEVERAL_EMPTY_FIELDS_ERROR
                errorMessage = "Choose category and enter spent amount."
            }

            isCategoryEmpty && isLimitEmpty -> {
                errorType = ErrorType.SEVERAL_EMPTY_FIELDS_ERROR
                errorMessage = "Choose category and enter limit."
            }

            isSpentEmpty && isLimitEmpty -> {
                errorType = ErrorType.SEVERAL_EMPTY_FIELDS_ERROR
                errorMessage = "Enter spent amount and limit."
            }

            isCategoryEmpty -> {
                errorType = ErrorType.EMPTY_CATEGORY_ERROR
                errorMessage = "Choose category."
            }

            isSpentEmpty -> {
                errorType = ErrorType.EMPTY_SPENT_AMOUNT
                errorMessage = "Choose spent amount."
            }

            isLimitEmpty -> {
                errorType = ErrorType.EMPTY_LIMIT_AMOUNT
                errorMessage = "Choose limit."
            }

            isCorrectRange -> {
                errorType = ErrorType.INCORRECT_AMOUNT_RANGE
                errorMessage = "Enter the correct range."
            }

            else -> {
                errorType = null
                errorMessage = null
            }
        }

        _budgetState.update {
            it.copy(
                errorType = errorType ?: it.errorType,
                errorMessage = errorMessage
            )
        }
    }

    data class BudgetState(
        val category: Category = Category.EMPTY_CATEGORY,
        val spentAmount: Double = 0.0,
        val startDate: String = formatTransactionDate(System.currentTimeMillis()),
        val endDate: String = formatTransactionDate(System.currentTimeMillis().plus(24 * 60 * 60 * 1000)),
        val limitAmount: Double = 0.0,
        val errorMessage: String? = null,
        val errorType: ErrorType = ErrorType.SEVERAL_EMPTY_FIELDS_ERROR,
        val isCreated: Boolean = false,
    )

    enum class ErrorType {
        EMPTY_CATEGORY_ERROR,
        EMPTY_SPENT_AMOUNT,
        EMPTY_LIMIT_AMOUNT,
        SEVERAL_EMPTY_FIELDS_ERROR,
        INCORRECT_AMOUNT_RANGE,
    }
}
