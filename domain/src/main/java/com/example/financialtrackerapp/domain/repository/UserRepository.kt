package com.example.financialtrackerapp.domain.repository

import com.example.financialtrackerapp.domain.model.User

interface UserRepository {
    suspend fun insert(user: User)
    suspend fun update(user: User)
    suspend fun delete(userId: Long)
    suspend fun getUserById(userId: Long): User?
    suspend fun getUserByUsername(username: String): User?
}