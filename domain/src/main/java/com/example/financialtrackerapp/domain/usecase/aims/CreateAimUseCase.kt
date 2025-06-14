package com.example.financialtrackerapp.domain.usecase.aims

import com.example.financialtrackerapp.domain.model.Aim
import com.example.financialtrackerapp.domain.repository.AimRepository
import com.example.financialtrackerapp.domain.repository.GlobalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateAimUseCase(
    private val aimsRepository: AimRepository,
    private val globalRepository: GlobalRepository,
) {
    suspend operator fun invoke(name: String, targetAmount: Double, savedAmount: Double) =
        withContext(Dispatchers.IO) {
            val isCreated = aimsRepository.insert(
                Aim(
                    id = 0,
                    accountId = globalRepository.getCurrentAccountId(),
                    name = name,
                    targetAmount = targetAmount,
                    savedAmount = savedAmount,
                )
            )

            return@withContext isCreated
        }
}