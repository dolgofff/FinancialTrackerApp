package com.example.financialtrackerapp.data.repository

import com.example.financialtrackerapp.data.dao.AdviceDao
import com.example.financialtrackerapp.domain.model.Advice
import com.example.financialtrackerapp.domain.repository.AdviceRepository

class AdviceRepositoryImpl(private val adviceDao: AdviceDao): AdviceRepository {
    override suspend fun insert(advice: Advice) {
        TODO("Not yet implemented")
    }

    override suspend fun update(advice: Advice) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(adviceId: Long) {
        TODO("Not yet implemented")
    }
}