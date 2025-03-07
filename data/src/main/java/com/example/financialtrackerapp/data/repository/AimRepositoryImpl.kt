package com.example.financialtrackerapp.data.repository

import com.example.financialtrackerapp.data.dao.AimDao
import com.example.financialtrackerapp.domain.model.Aim
import com.example.financialtrackerapp.domain.repository.AimRepository
import kotlinx.coroutines.flow.Flow

class AimRepositoryImpl(private val aimDao: AimDao): AimRepository {
    override suspend fun insert(aim: Aim) {
        TODO("Not yet implemented")
    }

    override suspend fun update(aim: Aim) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(aimId: Long) {
        TODO("Not yet implemented")
    }

    override fun getAimsByUserId(userId: Long): Flow<List<Aim>> {
        TODO("Not yet implemented")
    }
}