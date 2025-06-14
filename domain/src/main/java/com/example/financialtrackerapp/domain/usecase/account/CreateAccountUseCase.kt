package com.example.financialtrackerapp.domain.usecase.account

import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.model.CrossRef
import com.example.financialtrackerapp.domain.model.enums.AccountType
import com.example.financialtrackerapp.domain.model.enums.Currency
import com.example.financialtrackerapp.domain.repository.AccountRepository
import com.example.financialtrackerapp.domain.repository.CrossRefRepository
import com.example.financialtrackerapp.domain.repository.GlobalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateAccountUseCase(
    private val accountRepository: AccountRepository,
    private val crossRefRepository: CrossRefRepository,
    private val globalRepository: GlobalRepository,
) {
    suspend operator fun invoke(
        name: String,
        type: AccountType,
        currency: Currency,
        balance: Double,
    ): Long = withContext(Dispatchers.IO) {
        val accountId = accountRepository.insert(
            Account(
                id = 0,
                name = name,
                type = type,
                currency = currency,
                balance = balance,
            )
        )

        crossRefRepository.insert(
            CrossRef(
                accountId = accountId,
                userId = globalRepository.getCurrentAccountId(),
            )
        )

        return@withContext accountId
    }
}