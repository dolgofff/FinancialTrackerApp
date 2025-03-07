package com.example.financialtrackerapp.data.repository

import com.example.financialtrackerapp.data.dao.UserDao
import com.example.financialtrackerapp.domain.model.User
import com.example.financialtrackerapp.domain.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao): UserRepository {
    override suspend fun insert(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun update(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(userId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(userId: Long): User? {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByUsername(username: String): User? {
        TODO("Not yet implemented")
    }
}