package com.example.financialtrackerapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.financialtrackerapp.data.entity.AccountEntity
import com.example.financialtrackerapp.data.entity.UserEntity
import com.example.financialtrackerapp.data.entity.relations.AccountWithUsers
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAccount(accountEntity: AccountEntity): Long

    @Update
    suspend fun updateAccount(accountEntity: AccountEntity): Int

    @Delete
    suspend fun deleteAccount(accountEntity: AccountEntity): Int

    @Query("DELETE FROM accounts WHERE id = :accountId")
    suspend fun deleteAccountById(accountId: Long): Int

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    suspend fun getAccountById(accountId: Long): AccountEntity?

    @Query("SELECT * from accounts WHERE name = :accountName")
    suspend fun getAccountByName(accountName: String): AccountEntity?

    @Transaction
    @Query("SELECT * FROM accounts WHERE id = :accountId")
    suspend fun getAccountWithUsers(accountId: Long): AccountWithUsers?

    @Transaction
    @Query(
        """
    SELECT users.* FROM users
    INNER JOIN account_users ON users.id = account_users.userId
    WHERE account_users.accountId = :accountId
"""
    )
    fun getAccountsOwners(accountId: Long): Flow<List<UserEntity>>
}
