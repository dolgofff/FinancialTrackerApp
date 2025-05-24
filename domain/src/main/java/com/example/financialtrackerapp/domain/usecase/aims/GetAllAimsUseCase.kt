package com.example.financialtrackerapp.domain.usecase.aims

import com.example.financialtrackerapp.domain.repository.AimRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllAimsUseCase(private val aimsRepository: AimRepository) {
    suspend operator fun invoke(accountId: Long) = withContext(Dispatchers.IO) {
        aimsRepository.getAll(accountId)
    }
}