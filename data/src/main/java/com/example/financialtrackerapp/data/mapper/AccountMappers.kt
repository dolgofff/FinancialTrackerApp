package com.example.financialtrackerapp.data.mapper

import com.example.financialtrackerapp.data.entity.AccountEntity
import com.example.financialtrackerapp.data.entity.AccountUserCrossRef
import com.example.financialtrackerapp.data.entity.relations.AccountWithUsers
import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.model.AccountsUsers
import com.example.financialtrackerapp.domain.model.CrossRef
import com.example.financialtrackerapp.domain.model.enums.AccountType
import com.example.financialtrackerapp.domain.model.enums.Currency

fun AccountEntity.toDomain(): Account {
    return Account(
        id = id,
        name = name,
        type = AccountType.valueOf(type),
        currency = Currency.valueOf(currency),
        balance = balance,
    )
}

fun Account.toEntity(): AccountEntity {
    return AccountEntity(
        id = id,
        name = name,
        type = type.name,
        currency = currency.name,
        balance = balance,
    )
}

fun AccountWithUsers.toDomain(): AccountsUsers {
    return AccountsUsers(
        account = account.toDomain(),
        listOfUsers = users.map { it.toDomain() }
    )
}

fun AccountsUsers.toEntity(): AccountWithUsers {
    return AccountWithUsers(
        account = account.toEntity(),
        users = listOfUsers.map { it.toEntity() }
    )
}

fun AccountUserCrossRef.toDomain(): CrossRef {
    return CrossRef(
        accountId = accountId,
        userId = userId,
    )
}

fun CrossRef.toEntity(): AccountUserCrossRef {
    return AccountUserCrossRef(
        accountId = accountId,
        userId = userId
    )
}
