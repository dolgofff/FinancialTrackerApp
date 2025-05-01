package com.example.financialtrackerapp.domain.usecase.security

import com.example.financialtrackerapp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ForgottenPasswordUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String): String {
        return withContext(Dispatchers.IO) {
            userRepository.getByUsername(username)?.passwordHint
                ?: "You haven't left any hint."
        }
    }
}