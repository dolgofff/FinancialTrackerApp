package com.example.financialtrackerapp.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.example.financialtrackerapp.data.dao.BudgetDao
import com.example.financialtrackerapp.data.mapper.toDomain
import com.example.financialtrackerapp.data.mapper.toEntity
import com.example.financialtrackerapp.domain.model.Budget
import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BudgetRepositoryImpl(private val budgetDao: BudgetDao) : BudgetRepository {
    override suspend fun insert(budget: Budget): Boolean {
        return try {
            val id = budgetDao.insertBudget(budget.toEntity())
            id > 0
        } catch (e: SQLiteConstraintException) {
            false
        }
    }

    override suspend fun update(budget: Budget): Boolean {
        return budgetDao.updateBudget(budget.toEntity()) > 0
    }

    override suspend fun delete(budget: Budget): Boolean {
        return budgetDao.deleteBudget(budget.toEntity()) > 0
    }

    override suspend fun deleteById(budgetId: Long): Boolean {
        return budgetDao.deleteBudgetById(budgetId) > 0
    }

    override suspend fun getById(budgetId: Long): Budget? {
        return budgetDao.getBudgetById(budgetId)?.toDomain()
    }

    override fun getAll(accountId: Long): Flow<List<Budget>> {
        return budgetDao.getAccountsBudgets(accountId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getByCategory(category: Category): Budget? {
        return budgetDao.getBudgetByCategory(category.name)?.toDomain()
    }
}