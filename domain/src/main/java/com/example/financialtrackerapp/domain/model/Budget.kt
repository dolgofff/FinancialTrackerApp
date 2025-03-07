package com.example.financialtrackerapp.domain.model

import com.example.financialtrackerapp.domain.model.enums.Category

data class Budget(
    val id: Long = 0,
    val accountId: Long,
    val category: Category,
    val spentAmount: Double = 0.0,
    val limitAmount: Double,
    val startDate: Long = System.currentTimeMillis(),
    val endDate: Long = System.currentTimeMillis(),
)