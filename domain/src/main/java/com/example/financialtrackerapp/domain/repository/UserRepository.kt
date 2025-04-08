package com.example.financialtrackerapp.domain.repository

import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.model.User
import com.example.financialtrackerapp.domain.model.UsersAccounts
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insert(user: User): Boolean
    suspend fun update(user: User): Boolean
    suspend fun delete(user: User): Boolean
    suspend fun deleteById(userId: Long): Boolean
    suspend fun getById(userId: Long): User?
    suspend fun getByUsername(username: String): User?
    suspend fun getUserWithAccounts(userId: Long): UsersAccounts?
    fun getAllUsersAccounts(userId: Long): Flow<List<Account>>
}