package com.example.financialtrackerapp.domain.repository

import com.example.financialtrackerapp.domain.model.Budget
import com.example.financialtrackerapp.domain.model.enums.Category
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    suspend fun insert(budget: Budget): Boolean
    suspend fun update(budget: Budget): Boolean
    suspend fun delete(budget: Budget): Boolean
    suspend fun deleteById(budgetId: Long): Boolean
    suspend fun getById(budgetId: Long): Budget?
    suspend fun getByCategory(category: Category): Budget?
    fun getAll(accountId: Long): Flow<List<Budget>>
}