package com.example.financialtrackerapp.domain.usecase.security

import com.example.financialtrackerapp.domain.repository.AuthRepository
import com.example.financialtrackerapp.domain.repository.GlobalRepository
import com.example.financialtrackerapp.domain.repository.UserRepository
import com.example.financialtrackerapp.domain.utils.SecurityUtils

class AuthenticationUseCase(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val globalRepository: GlobalRepository,
) {

    suspend operator fun invoke(username: String, password: String): Boolean {
        val user = userRepository.getByUsername(username)
        return user?.let {
            if (user.passwordHash == SecurityUtils.hashPassword(password, user.passwordSalt)) {
                authRepository.saveToken(SecurityUtils.generateToken())
                globalRepository.saveUsername(username)
                true
            } else false
        } ?: false
    }
}