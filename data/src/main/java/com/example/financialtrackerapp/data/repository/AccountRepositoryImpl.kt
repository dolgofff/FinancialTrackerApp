package com.example.financialtrackerapp.data.repository

import com.example.financialtrackerapp.data.dao.AccountDao
import com.example.financialtrackerapp.data.mapper.toEntity

import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

class AccountRepositoryImpl(private val accountDao: AccountDao) : AccountRepository {
    override suspend fun insert(account: Account) {
        accountDao.insertAccount(account.toEntity())
    }

    override suspend fun update(account: Account) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(accountId: Long) {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<Account>> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(accountId: Long): Account? {
        TODO("Not yet implemented")
    }
}