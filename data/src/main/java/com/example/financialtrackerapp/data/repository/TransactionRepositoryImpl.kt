package com.example.financialtrackerapp.data.repository

import com.example.financialtrackerapp.data.dao.TransactionDao
import com.example.financialtrackerapp.domain.model.Transaction
import com.example.financialtrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(private val transactionDao: TransactionDao): TransactionRepository {
    override suspend fun insert(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun update(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(transactionId: Long) {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override fun getByAccountId(accountId: Long): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }
}