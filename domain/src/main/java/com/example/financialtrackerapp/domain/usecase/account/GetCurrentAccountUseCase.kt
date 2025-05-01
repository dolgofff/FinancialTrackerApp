package com.example.financialtrackerapp.domain.usecase.account

import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.repository.AccountRepository
import com.example.financialtrackerapp.domain.repository.GlobalRepository

class GetCurrentAccountUseCase(
    private val globalRepository: GlobalRepository,
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Account? {
        val accountId = globalRepository.getCurrentAccountId()
        return accountRepository.getAccountById(accountId)
    }
}