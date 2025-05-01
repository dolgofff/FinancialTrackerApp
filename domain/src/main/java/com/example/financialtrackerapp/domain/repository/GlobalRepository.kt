package com.example.financialtrackerapp.domain.repository

interface GlobalRepository {
    suspend fun getUsername(): String
    suspend fun getCurrentAccountId(): Long
    suspend fun saveUsername(username: String)
    suspend fun saveCurrentAccountId(currentAccountId: Long)
}