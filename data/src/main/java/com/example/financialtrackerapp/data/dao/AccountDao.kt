package com.example.financialtrackerapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.financialtrackerapp.data.entity.AccountEntity

@Dao
interface AccountDao {
    @Insert
    suspend fun insertAccount(account: AccountEntity)

    @Query("SELECT * FROM accounts")
    suspend fun getAllAccounts(): List<AccountEntity>
}