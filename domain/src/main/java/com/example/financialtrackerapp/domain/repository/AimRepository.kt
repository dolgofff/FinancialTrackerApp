package com.example.financialtrackerapp.domain.repository

import com.example.financialtrackerapp.domain.model.Aim
import kotlinx.coroutines.flow.Flow

interface AimRepository {
    suspend fun insert(aim: Aim)
    suspend fun update(aim: Aim)
    suspend fun delete(aimId: Long)
    fun getAimsByUserId(userId: Long): Flow<List<Aim>>
}