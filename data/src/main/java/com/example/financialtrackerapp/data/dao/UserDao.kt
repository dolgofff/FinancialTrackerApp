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
import com.example.financialtrackerapp.data.entity.relations.UserWithAccounts
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(userEntity: UserEntity): Long

    @Update
    suspend fun updateUser(userEntity: UserEntity): Int

    @Delete
    suspend fun deleteUser(userEntity: UserEntity): Int

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Long): Int

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Long): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserWithAccounts(userId: Long): UserWithAccounts?

    @Transaction
    @Query("""
    SELECT accounts.* FROM accounts
    INNER JOIN account_users ON accounts.id = account_users.accountId
    WHERE account_users.userId = :userId
""")
    fun getAllUsersAccounts(userId: Long): Flow<List<AccountEntity>>
}