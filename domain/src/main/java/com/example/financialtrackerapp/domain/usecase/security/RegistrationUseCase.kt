package com.example.financialtrackerapp.domain.usecase.security

import com.example.financialtrackerapp.domain.model.User
import com.example.financialtrackerapp.domain.repository.UserRepository
import com.example.financialtrackerapp.domain.utils.SecurityUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegistrationUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String, password: String, passwordHint: String): Boolean {
        val (salt, hashedPassword) = withContext(Dispatchers.Default) {
            val salt = SecurityUtils.generateSalt()
            val hashedPassword = SecurityUtils.hashPassword(password = password, salt = salt)
            salt to hashedPassword
        }

        return withContext(Dispatchers.IO) {
            userRepository.insert(
                User(
                    id = 0,
                    username = username,
                    passwordHash = hashedPassword,
                    passwordSalt = salt,
                    passwordHint = passwordHint,
                )
            )
        }
    }
}