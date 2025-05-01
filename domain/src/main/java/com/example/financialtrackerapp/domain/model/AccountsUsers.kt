package com.example.financialtrackerapp.domain.model

data class AccountsUsers(
    val account: Account,
    val listOfUsers: List<User> = emptyList()
)
