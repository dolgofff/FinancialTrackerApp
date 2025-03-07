package com.example.financialtrackerapp.data.mapper

import com.example.financialtrackerapp.data.entity.TransactionEntity
import com.example.financialtrackerapp.domain.model.Transaction
import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.domain.model.enums.TransactionType

fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        accountId = accountId,
        amount = amount,
        category = Category.valueOf(category),
        type = TransactionType.valueOf(type),
        date = date,
        note = note,
        place = place
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        accountId = accountId,
        amount = amount,
        category = category.name,
        type = type.name,
        date = date,
        note = note,
        place = place
    )
}