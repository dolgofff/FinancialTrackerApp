package com.example.financialtrackerapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.financialtrackerapp.data.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Insert
    suspend fun insertBudget(budgetEntity: BudgetEntity): Long

    @Update
    suspend fun updateBudget(budgetEntity: BudgetEntity): Int

    @Delete
    suspend fun deleteBudget(budgetEntity: BudgetEntity): Int

    @Query("DELETE FROM budgets WHERE id = :budgetId")
    suspend fun deleteBudgetById(budgetId: Long): Int

    @Query("SELECT * FROM budgets WHERE id = :budgetId")
    suspend fun getBudgetById(budgetId: Long): BudgetEntity?

    @Query("SELECT * FROM budgets WHERE accountId = :accountId")
    fun getAccountsBudgets(accountId: Long): Flow<List<BudgetEntity>>

    @Query("SELECT * FROM budgets WHERE category = :category")
    fun getBudgetByCategory(category: String): BudgetEntity?
}