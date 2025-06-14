package com.example.financialtrackerapp.domain.usecase.budgets

import com.example.financialtrackerapp.domain.model.Budget
import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.domain.repository.BudgetRepository
import com.example.financialtrackerapp.domain.repository.GlobalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateBudgetUseCase(
    private val budgetsRepository: BudgetRepository,
    private val globalRepository: GlobalRepository
) {
    suspend operator fun invoke(
        category: Category,
        spentAmount: Double,
        limitAmount: Double,
        startDate: Long,
        endDate: Long
    ) = withContext(Dispatchers.IO) {
        val isCreated = budgetsRepository.insert(
            Budget(
                id = 0,
                accountId = globalRepository.getCurrentAccountId(),
                category = category,
                spentAmount = spentAmount,
                limitAmount = limitAmount,
                startDate = startDate,
                endDate = endDate,
            )
        )
        return@withContext isCreated
    }
}