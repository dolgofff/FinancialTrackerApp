package com.example.financialtrackerapp.data.mapper

import com.example.financialtrackerapp.data.entity.AccountEntity
import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.model.enums.AccountType
import com.example.financialtrackerapp.domain.model.enums.Currency

fun AccountEntity.toDomain(): Account {
    return Account(
        id = id,
        name = name,
        type = AccountType.valueOf(type),
        currency = Currency.valueOf(currency),
        balance = balance
    )
}

fun Account.toEntity(): AccountEntity {
    return AccountEntity(
        id = id,
        name = name,
        type = type.name,
        currency = currency.name,
        balance = balance
    )
}