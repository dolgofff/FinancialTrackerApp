package com.example.financialtrackerapp.domain.usecase.account

import com.example.financialtrackerapp.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteAccountByIdUseCase(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(accountId: Long): Boolean = withContext(Dispatchers.IO) {
        accountRepository.deleteById(accountId)
    }

}