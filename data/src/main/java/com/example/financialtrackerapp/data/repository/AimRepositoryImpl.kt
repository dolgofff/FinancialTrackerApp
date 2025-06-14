package com.example.financialtrackerapp.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.example.financialtrackerapp.data.dao.AimDao
import com.example.financialtrackerapp.data.mapper.toDomain
import com.example.financialtrackerapp.data.mapper.toEntity
import com.example.financialtrackerapp.domain.model.Aim
import com.example.financialtrackerapp.domain.repository.AimRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AimRepositoryImpl(private val aimDao: AimDao) : AimRepository {
    override suspend fun insert(aim: Aim): Boolean {
        return try {
            val id = aimDao.insertAim(aim.toEntity())
            id > 0
        } catch (e: SQLiteConstraintException) {
            false
        }
    }

    override suspend fun update(aim: Aim): Boolean {
        return aimDao.updateAim(aim.toEntity()) > 0
    }

    override suspend fun delete(aim: Aim): Boolean {
        return aimDao.deleteAim(aim.toEntity()) > 0
    }

    override suspend fun deleteById(aimId: Long): Boolean {
        return aimDao.deleteAimById(aimId) > 0
    }

    override suspend fun getById(aimId: Long): Aim? {
        return aimDao.getAimById(aimId)?.toDomain()
    }

    override fun getAll(accountId: Long): Flow<List<Aim>> {
        return aimDao.getAccountsAims(accountId).map { list -> list.map { it.toDomain() } }
    }
}