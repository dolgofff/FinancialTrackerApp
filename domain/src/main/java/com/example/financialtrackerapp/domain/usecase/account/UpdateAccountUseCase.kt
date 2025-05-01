package com.example.financialtrackerapp.domain.usecase.account

import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.repository.AccountRepository

class UpdateAccountUseCase(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(account: Account){
        accountRepository.update(account)
    }
}