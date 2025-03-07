package com.example.financialtrackerapp.domain.repository

import com.example.financialtrackerapp.domain.model.Advice
import com.example.financialtrackerapp.domain.model.enums.AdviceType
import kotlinx.coroutines.flow.Flow

interface AdviceRepository {
    suspend fun insert(advice: Advice)
    suspend fun update(advice: Advice)
    suspend fun delete(adviceId: Long)

}