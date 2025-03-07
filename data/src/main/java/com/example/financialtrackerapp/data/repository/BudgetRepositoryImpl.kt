package com.example.financialtrackerapp.data.repository

import com.example.financialtrackerapp.data.dao.BudgetDao
import com.example.financialtrackerapp.domain.model.Budget
import com.example.financialtrackerapp.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow

class BudgetRepositoryImpl(private val budgetDao: BudgetDao): BudgetRepository {
    override suspend fun insert(budget: Budget) {
        TODO("Not yet implemented")
    }

    override suspend fun update(budget: Budget) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(budgetId: Long) {
        TODO("Not yet implemented")
    }

    override fun getBudgetsByUserId(userId: Long): Flow<List<Budget>> {
        TODO("Not yet implemented")
    }
}