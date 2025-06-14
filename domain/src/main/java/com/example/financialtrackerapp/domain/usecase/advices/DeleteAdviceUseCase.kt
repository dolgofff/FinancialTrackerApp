package com.example.financialtrackerapp.domain.usecase.advices

import com.example.financialtrackerapp.domain.model.Advice
import com.example.financialtrackerapp.domain.repository.AdviceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteAdviceUseCase(private val adviceRepository: AdviceRepository) {
    suspend operator fun invoke(toDelete: Advice) = withContext(Dispatchers.IO) {
        adviceRepository.delete(toDelete)
    }
}