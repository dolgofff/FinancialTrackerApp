package com.example.financialtrackerapp.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.example.financialtrackerapp.data.dao.AdviceDao
import com.example.financialtrackerapp.data.mapper.toDomain
import com.example.financialtrackerapp.data.mapper.toEntity
import com.example.financialtrackerapp.domain.model.Advice
import com.example.financialtrackerapp.domain.model.enums.AdviceType
import com.example.financialtrackerapp.domain.repository.AdviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AdviceRepositoryImpl(private val adviceDao: AdviceDao) : AdviceRepository {
    override suspend fun insert(advice: Advice): Boolean {
        return try {
            val id = adviceDao.insertAdvice(advice.toEntity())
            id > 0
        } catch (e: SQLiteConstraintException) {
            false
        }
    }

    override suspend fun update(advice: Advice): Boolean {
        return adviceDao.updateAdvice(advice.toEntity()) > 0
    }

    override suspend fun delete(advice: Advice): Boolean {
        return adviceDao.deleteAdvice(advice.toEntity()) > 0
    }

    override suspend fun deleteById(adviceId: Long): Boolean {
        return adviceDao.deleteAdviceById(adviceId) > 0
    }

    override suspend fun getById(adviceId: Long): Advice? {
        return adviceDao.getAdviceById(adviceId)?.toDomain()
    }

    override fun getByType(adviceType: AdviceType): Flow<List<Advice>> {
        return adviceDao.getAdvicesByType(adviceType.name)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun getAll(accountId: Long): Flow<List<Advice>> {
        return adviceDao.getAccountsAdvices(accountId).map { list -> list.map { it.toDomain() } }
    }

}