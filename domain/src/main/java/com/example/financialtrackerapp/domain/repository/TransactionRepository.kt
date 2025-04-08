package com.example.financialtrackerapp.domain.repository

import com.example.financialtrackerapp.domain.model.Transaction
import kotlinx.coroutines.flow.Flow


interface TransactionRepository {
    suspend fun insert(transaction: Transaction): Boolean
    suspend fun update(transaction: Transaction): Boolean
    suspend fun delete(transaction: Transaction): Boolean
    suspend fun deleteById(transactionId: Long): Boolean
    suspend fun getById(transactionId: Long): Transaction?
    fun getByDate(transactionDate: Long): Flow<List<Transaction>>
    fun getAll(accountId: Long): Flow<List<Transaction>>
}