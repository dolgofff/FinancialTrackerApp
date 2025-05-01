package com.example.financialtrackerapp.domain.model

import com.example.financialtrackerapp.domain.model.enums.AccountType
import com.example.financialtrackerapp.domain.model.enums.Currency

data class Account(
    val id: Long = 0,
    val name: String,
    val type: AccountType = AccountType.SINGLE,
    val currency: Currency = Currency.USD,
    val balance: Double = 0.0,
)







