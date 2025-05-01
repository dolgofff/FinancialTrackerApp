package com.example.financialtrackerapp.data.entity.temporary

import kotlinx.serialization.Serializable

@Serializable
data class TokenData(
    val token: String = "none",
    val lastActive: Long = System.currentTimeMillis(),
)