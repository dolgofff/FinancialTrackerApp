package com.example.financialtrackerapp.domain.model

import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.domain.model.enums.TransactionType

data class Transaction(
    val id: Long = 0L,
    val accountId: Long,
    val amount: Double = 0.0,
    val category: Category = Category.EMPTY_CATEGORY,
    val type: TransactionType = TransactionType.EXPENSE,
    val date: Long = System.currentTimeMillis(),
    val note: String? = null,
    val place: String? = null,
)