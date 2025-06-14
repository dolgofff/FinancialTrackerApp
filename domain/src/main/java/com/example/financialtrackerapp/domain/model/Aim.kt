package com.example.financialtrackerapp.domain.model

data class Aim(
    val id: Long = 0L,
    val accountId: Long,
    val name: String,
    val targetAmount: Double,
    val savedAmount: Double = 0.0,
)