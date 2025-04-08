package com.example.financialtrackerapp.domain.usecase.security

import com.example.financialtrackerapp.domain.repository.GlobalRepository

class InitializeUserUseCase(private val globalRepository: GlobalRepository) {
    suspend operator fun invoke(): String = globalRepository.getUsername()
}