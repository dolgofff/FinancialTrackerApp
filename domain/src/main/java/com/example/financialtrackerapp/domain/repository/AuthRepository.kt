package com.example.financialtrackerapp.domain.repository

interface AuthRepository {
    suspend fun saveToken(token: String)
    suspend fun getToken(): String
    suspend fun getLastActive(): Long
    suspend fun isLoggedIn(): Boolean
    suspend fun updateLastActive()
    suspend fun shouldLogout(): Boolean
    suspend fun logout()
}