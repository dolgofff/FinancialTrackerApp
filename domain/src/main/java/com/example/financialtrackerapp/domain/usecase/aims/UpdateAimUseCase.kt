package com.example.financialtrackerapp.domain.usecase.aims

import com.example.financialtrackerapp.domain.model.Aim
import com.example.financialtrackerapp.domain.repository.AimRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateAimUseCase(private val aimRepository: AimRepository) {
    suspend operator fun invoke(newAim: Aim) = withContext(Dispatchers.IO) {
        if (newAim.savedAmount >= newAim.targetAmount) {
            aimRepository.delete(newAim)
        } else {
            aimRepository.update(newAim)
        }
    }
}