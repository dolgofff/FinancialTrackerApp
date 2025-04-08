package com.example.financialtrackerapp.domain.repository

import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.model.AccountsUsers
import com.example.financialtrackerapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun insert(account: Account): Boolean
    suspend fun update(account: Account): Boolean
    suspend fun delete(account: Account): Boolean
    suspend fun getAccountById(accountId: Long): Account?
    suspend fun deleteById(accountId: Long): Boolean
    suspend fun getAccountWithUsers(accountId: Long): AccountsUsers?
    fun getAccountsOwners(accountId: Long): Flow<List<User>>
}