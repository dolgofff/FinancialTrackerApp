package com.example.financialtrackerapp.domain.repository

import com.example.financialtrackerapp.domain.model.Advice
import com.example.financialtrackerapp.domain.model.enums.AdviceType
import kotlinx.coroutines.flow.Flow

interface AdviceRepository {
    suspend fun insert(advice: Advice): Boolean
    suspend fun update(advice: Advice): Boolean
    suspend fun delete(advice: Advice): Boolean
    suspend fun deleteById(adviceId: Long): Boolean
    suspend fun getById(adviceId: Long): Advice?
    fun getByType(adviceType: AdviceType): Flow<List<Advice>>
    fun getAll(accountId: Long): Flow<List<Advice>>
}