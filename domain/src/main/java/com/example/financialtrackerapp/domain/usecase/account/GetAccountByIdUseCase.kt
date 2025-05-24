package com.example.financialtrackerapp.domain.usecase.account

import com.example.financialtrackerapp.domain.repository.AccountRepository

class GetAccountByIdUseCase(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(accountId: Long) = accountRepository.getAccountById(accountId)

}