package com.example.financialtrackerapp.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.example.financialtrackerapp.data.dao.AccountDao
import com.example.financialtrackerapp.data.mapper.toDomain
import com.example.financialtrackerapp.data.mapper.toEntity
import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.model.AccountsUsers
import com.example.financialtrackerapp.domain.model.User
import com.example.financialtrackerapp.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountRepositoryImpl(private val accountDao: AccountDao) : AccountRepository {
    override suspend fun insert(account: Account): Boolean {
        val existingAccount = accountDao.getAccountByName(account.name)

        return if (existingAccount == null) {
            try {
                val id = accountDao.insertAccount(account.toEntity())
                id > 0
            } catch (e: SQLiteConstraintException) {
                false
            }
        } else {
            false
        }
    }

    override suspend fun update(account: Account): Boolean {
        return accountDao.updateAccount(account.toEntity()) > 0
    }

    override suspend fun delete(account: Account): Boolean {
        return accountDao.deleteAccount(account.toEntity()) > 0
    }

    override suspend fun deleteById(accountId: Long): Boolean {
        return accountDao.deleteAccountById(accountId) > 0
    }

    override suspend fun getAccountById(accountId: Long): Account? {
        return accountDao.getAccountById(accountId)?.toDomain()
    }

    override suspend fun getAccountWithUsers(accountId: Long): AccountsUsers? {
        return accountDao.getAccountWithUsers(accountId)?.toDomain()
    }

    override fun getAccountsOwners(accountId: Long): Flow<List<User>> {
        return accountDao.getAccountsOwners(accountId).map { list -> list.map { it.toDomain() } }
    }


}