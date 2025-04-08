package com.example.financialtrackerapp.domain.model

data class User(
    val id: Long = 0L,
    val username: String,
    val passwordHash: String,
    val passwordSalt: String,
    val passwordHint: String = "",
)
