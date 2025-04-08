package com.example.financialtrackerapp.domain.usecase.security

import com.example.financialtrackerapp.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        withContext(Dispatchers.IO) {
            if (authRepository.shouldLogout()) {
                authRepository.logout()
            }
        }
    }
}