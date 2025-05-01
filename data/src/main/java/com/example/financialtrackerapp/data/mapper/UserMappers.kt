package com.example.financialtrackerapp.data.mapper

import com.example.financialtrackerapp.data.entity.UserEntity
import com.example.financialtrackerapp.data.entity.relations.UserWithAccounts
import com.example.financialtrackerapp.domain.model.User
import com.example.financialtrackerapp.domain.model.UsersAccounts

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        username = username,
        passwordHash = passwordHash,
        passwordSalt = passwordSalt,
        passwordHint = passwordHint,
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        passwordHash = passwordHash,
        passwordSalt = passwordSalt,
        passwordHint = passwordHint,
    )
}

fun UserWithAccounts.toDomain(): UsersAccounts {
    return UsersAccounts(
        user = user.toDomain(),
        listOfAccounts = accounts.map { it.toDomain() },
    )
}

fun UsersAccounts.toEntity(): UserWithAccounts {
    return UserWithAccounts(
        user = user.toEntity(),
        accounts = listOfAccounts.map { it.toEntity() }
    )
}