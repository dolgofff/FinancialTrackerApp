package com.example.financialtrackerapp.domain.repository

import com.example.financialtrackerapp.domain.model.Aim
import kotlinx.coroutines.flow.Flow

interface AimRepository {
    suspend fun insert(aim: Aim): Boolean
    suspend fun update(aim: Aim): Boolean
    suspend fun delete(aim: Aim): Boolean
    suspend fun deleteById(aimId: Long): Boolean
    suspend fun getById(aimId: Long): Aim?
    fun getAll(accountId: Long): Flow<List<Aim>>
}