package com.example.financialtrackerapp.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.example.financialtrackerapp.data.dao.AccountUserCrossRefDao
import com.example.financialtrackerapp.data.mapper.toEntity
import com.example.financialtrackerapp.domain.model.CrossRef
import com.example.financialtrackerapp.domain.repository.CrossRefRepository

class CrossRefRepositoryImpl(private val accountUserCrossRefDao: AccountUserCrossRefDao) :
    CrossRefRepository {
    override suspend fun insert(crossRef: CrossRef): Boolean {
        return try {
            val id = accountUserCrossRefDao.insert(crossRef.toEntity())
            id > 0
        } catch (e: SQLiteConstraintException) {
            false
        }
    }
}