package com.example.financialtrackerapp.domain.usecase.budgets

import com.example.financialtrackerapp.domain.repository.BudgetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllBudgetsUseCase(private val budgetRepository: BudgetRepository) {
    suspend operator fun invoke(accountId: Long) = withContext(Dispatchers.IO) {
        budgetRepository.getAll(accountId)
    }
}