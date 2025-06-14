package com.example.financialtrackerapp.domain.usecase.budgets

import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.domain.repository.BudgetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateSpentValueUseCase(private val budgetRepository: BudgetRepository) {
    suspend operator fun invoke(category: Category, amount: Long) = withContext(Dispatchers.IO) {
        val toUpdate = budgetRepository.getByCategory(category)

        toUpdate?.copy(spentAmount = toUpdate.spentAmount + amount)
            ?.let { budgetRepository.update(it) }
    }
}