package com.example.financialtrackerapp.data.mapper

import com.example.financialtrackerapp.data.entity.BudgetEntity
import com.example.financialtrackerapp.domain.model.Budget
import com.example.financialtrackerapp.domain.model.enums.Category

fun BudgetEntity.toDomain(): Budget {
    return Budget(
        id = id,
        accountId = accountId,
        category = Category.valueOf(category),
        spentAmount = spentAmount,
        limitAmount = limitAmount,
        startDate = startDate.toLong(),
        endDate = endDate.toLong()
    )
}

fun Budget.toEntity(): BudgetEntity {
    return BudgetEntity(
        id = id,
        accountId = accountId,
        category = category.name,
        spentAmount = spentAmount,
        limitAmount = limitAmount,
        startDate = startDate.toString(),
        endDate = endDate.toString()
    )
}