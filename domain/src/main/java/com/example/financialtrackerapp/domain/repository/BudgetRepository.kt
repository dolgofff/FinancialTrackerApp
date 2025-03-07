package com.example.financialtrackerapp.domain.repository

import com.example.financialtrackerapp.domain.model.Budget
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    suspend fun insert(budget: Budget)
    suspend fun update(budget: Budget)
    suspend fun delete(budgetId: Long)
    fun getBudgetsByUserId(userId: Long): Flow<List<Budget>>
}