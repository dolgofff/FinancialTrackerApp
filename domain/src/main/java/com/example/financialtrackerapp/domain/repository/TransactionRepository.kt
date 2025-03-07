package com.example.financialtrackerapp.domain.repository

import com.example.financialtrackerapp.domain.model.Transaction
import kotlinx.coroutines.flow.Flow


interface TransactionRepository {
    suspend fun insert(transaction: Transaction)
    suspend fun update(transaction: Transaction)
    suspend fun delete(transactionId: Long)
    fun getAll(): Flow<List<Transaction>>
    fun getByAccountId(accountId: Long): Flow<List<Transaction>>
}