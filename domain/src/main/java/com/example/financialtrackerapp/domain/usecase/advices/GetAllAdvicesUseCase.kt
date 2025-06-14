package com.example.financialtrackerapp.domain.usecase.advices

import com.example.financialtrackerapp.domain.repository.AdviceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllAdvicesUseCase(private val adviceRepository: AdviceRepository) {
    suspend operator fun invoke(accountId: Long) = withContext(Dispatchers.IO) {
        adviceRepository.getAll(accountId)
    }
}