package com.example.financialtrackerapp.domain.usecase.account

import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteAccountUseCase(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(account: Account): Boolean = withContext(Dispatchers.IO) {
        accountRepository.delete(account)
    }

}