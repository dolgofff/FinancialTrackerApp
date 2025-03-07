package com.example.financialtrackerapp.domain.repository

import com.example.financialtrackerapp.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun insert(account: Account)
    suspend fun update(account: Account)
    suspend fun delete(accountId: Long)
    fun getAll(): Flow<List<Account>>
    suspend fun getById(accountId: Long): Account?
}