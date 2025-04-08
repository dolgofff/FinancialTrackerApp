package com.example.financialtrackerapp.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.example.financialtrackerapp.data.dao.UserDao
import com.example.financialtrackerapp.data.entity.AccountEntity
import com.example.financialtrackerapp.data.mapper.toDomain
import com.example.financialtrackerapp.data.mapper.toEntity
import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.model.User
import com.example.financialtrackerapp.domain.model.UsersAccounts
import com.example.financialtrackerapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override suspend fun insert(user: User): Boolean {
        return try {
            val id = userDao.insertUser(user.toEntity())
            id > 0
        } catch (e: SQLiteConstraintException) {
            false
        }
    }

    override suspend fun update(user: User): Boolean {
        return userDao.updateUser(user.toEntity()) == 1
    }

    override suspend fun delete(user: User): Boolean {
        return userDao.deleteUser(user.toEntity()) > 0
    }

    override suspend fun deleteById(userId: Long): Boolean {
        return userDao.deleteUserById(userId) > 0
    }

    override suspend fun getByUsername(username: String): User? {
        return userDao.getUserByUsername(username)?.toDomain()
    }

    override suspend fun getById(userId: Long): User? {
        return userDao.getUserById(userId)?.toDomain()
    }

    override suspend fun getUserWithAccounts(userId: Long): UsersAccounts? {
        return userDao.getUserWithAccounts(userId)?.toDomain()
    }

    override fun getAllUsersAccounts(userId: Long): Flow<List<Account>> {
        return userDao.getAllUsersAccounts(userId).map { list -> list.map { it.toDomain() } }
    }

}