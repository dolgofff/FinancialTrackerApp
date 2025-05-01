package com.example.financialtrackerapp.data.entity.temporary

import kotlinx.serialization.Serializable

@Serializable
data class GlobalData(
    val username: String = "",
    val currentAccountId: Long = 0L
)