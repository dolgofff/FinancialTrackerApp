package com.example.financialtrackerapp.domain.usecase.account

import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.model.enums.AccountType
import com.example.financialtrackerapp.domain.model.enums.Currency
import com.example.financialtrackerapp.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateAccountUseCase(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(
        name: String,
        type: AccountType,
        currency: Currency,
        balance: Double
    ): Boolean = withContext(Dispatchers.IO) {
        accountRepository.insert(
            Account(
                id = 0,
                name = name,
                type = type,
                currency = currency,
                balance = balance
            )
        )
    }


}