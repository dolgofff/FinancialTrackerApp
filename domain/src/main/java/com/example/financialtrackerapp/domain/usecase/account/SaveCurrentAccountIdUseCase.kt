package com.example.financialtrackerapp.domain.usecase.account

import com.example.financialtrackerapp.domain.repository.GlobalRepository

class SaveCurrentAccountIdUseCase(private val globalRepository: GlobalRepository) {
    suspend operator fun invoke(currentAccountId: Long) {
        globalRepository.saveCurrentAccountId(currentAccountId)
    }
}