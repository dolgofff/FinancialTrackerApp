package com.example.financialtrackerapp.domain.usecase.security

import com.example.financialtrackerapp.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SplashUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Boolean = withContext(Dispatchers.IO) {
        if (authRepository.isLoggedIn() && (!authRepository.shouldLogout())) {
            authRepository.updateLastActive()
            true
        } else {
            false
        }
    }
}



