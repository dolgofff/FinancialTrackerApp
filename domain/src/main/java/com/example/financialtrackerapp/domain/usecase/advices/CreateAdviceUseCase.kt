package com.example.financialtrackerapp.domain.usecase.advices

import com.example.financialtrackerapp.domain.model.Advice
import com.example.financialtrackerapp.domain.model.enums.AdviceType
import com.example.financialtrackerapp.domain.repository.AdviceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateAdviceUseCase(private val adviceRepository: AdviceRepository) {
    suspend operator fun invoke(accountId: Long, type: AdviceType, content: String) =
        withContext(Dispatchers.IO) {
            adviceRepository.insert(
                Advice(
                    accountId = accountId,
                    type = type,
                    content = content
                )
            )
        }
}