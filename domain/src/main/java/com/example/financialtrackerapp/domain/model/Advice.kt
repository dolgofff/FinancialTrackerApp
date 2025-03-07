package com.example.financialtrackerapp.domain.model

import com.example.financialtrackerapp.domain.model.enums.AdviceType

data class Advice(
    val id: Long = 0,
    val accountId: Long,
    val type: AdviceType,
    val content: String,
)