package com.example.financialtrackerapp.domain.model

data class UsersAccounts(
    val user: User,
    val listOfAccounts: List<Account> = emptyList()
)