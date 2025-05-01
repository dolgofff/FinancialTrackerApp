package com.example.financialtrackerapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.financialtrackerapp.data.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertTransaction(transactionEntity: TransactionEntity): Long

    @Update
    suspend fun updateTransaction(transactionEntity: TransactionEntity): Int

    @Delete
    suspend fun deleteTransaction(transactionEntity: TransactionEntity): Int

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    suspend fun deleteTransactionById(transactionId: Long): Int

    @Query("SELECT * FROM transactions WHERE id = :transactionId")
    suspend fun getTransactionById(transactionId: Long): TransactionEntity?

    @Query("SELECT * FROM transactions WHERE date = :transactionsDate")
    fun getTransactionsByDate(transactionsDate: Long): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE accountId = :accountId order by date DESC")
    fun getAllAccountsTransactions(accountId: Long): Flow<List<TransactionEntity>>
}