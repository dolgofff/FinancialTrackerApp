package com.example.financialtrackerapp.domain.model

data class User(
    val id: Long = 0,
    val username: String,
    val passwordHash: String,
    val passwordSalt: String,
)
