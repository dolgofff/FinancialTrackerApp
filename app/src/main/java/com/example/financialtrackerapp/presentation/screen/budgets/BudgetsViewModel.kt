package com.example.financialtrackerapp.presentation.screen.budgets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtrackerapp.domain.model.Aim
import com.example.financialtrackerapp.domain.model.Budget
import com.example.financialtrackerapp.domain.usecase.account.GetCurrentAccountUseCase
import com.example.financialtrackerapp.domain.usecase.aims.GetAllAimsUseCase
import com.example.financialtrackerapp.domain.usecase.budgets.GetAllBudgetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetsViewModel @Inject constructor(
    private val getAllAimsUseCase: GetAllAimsUseCase,
    private val getAllBudgetsUseCase: GetAllBudgetsUseCase,
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase,
) : ViewModel() {

    private val _budgetsState = MutableStateFlow(BudgetsState())
    val budgetsState = _budgetsState.asStateFlow()

    init {
        loadBudgetsInfo()
    }

    private fun loadBudgetsInfo() {
        viewModelScope.launch {
            val currentAccount = getCurrentAccountUseCase()

            currentAccount?.let { account ->
                launch {
                    getAllAimsUseCase(account.id).collect { aims ->
                        _budgetsState.update { state ->
                            state.copy(aims = aims)
                        }
                    }
                }

                launch {
                    getAllBudgetsUseCase(account.id).collect { budgets ->
                        _budgetsState.update { state ->
                            state.copy(budgets = budgets)
                        }
                    }
                }
            }
        }
    }

    fun filter(order: Boolean) {
        if (order) {
            budgetsState.value.budgets.sortedByDescending { it.endDate }
        } else {
            budgetsState.value.budgets.sortedBy { it.endDate }
        }
    }

    data class BudgetsState(
        val aims: List<Aim> = emptyList(),
        val budgets: List<Budget> = emptyList()
    )
}
