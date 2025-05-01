package com.example.financialtrackerapp.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.example.financialtrackerapp.data.dao.TransactionDao
import com.example.financialtrackerapp.data.mapper.toDomain
import com.example.financialtrackerapp.data.mapper.toEntity
import com.example.financialtrackerapp.domain.model.Transaction
import com.example.financialtrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    TransactionRepository {
    override suspend fun insert(transaction: Transaction): Boolean {
        return try {
            val id = transactionDao.insertTransaction(transaction.toEntity())
            id > 0
        } catch (e: SQLiteConstraintException) {
            false
        }
    }

    override suspend fun update(transaction: Transaction): Boolean {
        return transactionDao.updateTransaction(transaction.toEntity()) > 0
    }

    override suspend fun delete(transaction: Transaction): Boolean {
        return transactionDao.deleteTransaction(transaction.toEntity()) > 0
    }

    override suspend fun deleteById(transactionId: Long): Boolean {
        return transactionDao.deleteTransactionById(transactionId) > 0
    }

    override suspend fun getById(transactionId: Long): Transaction? {
        return transactionDao.getTransactionById(transactionId)?.toDomain()
    }

    override fun getByDate(transactionDate: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByDate(transactionDate)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun getAll(accountId: Long): Flow<List<Transaction>> {
        return transactionDao.getAllAccountsTransactions(accountId)
            .map { list -> list.map { it.toDomain() } }
    }
}