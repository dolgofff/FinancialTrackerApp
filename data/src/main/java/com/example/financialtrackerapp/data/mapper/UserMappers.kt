package com.example.financialtrackerapp.data.mapper

import com.example.financialtrackerapp.data.entity.UserEntity
import com.example.financialtrackerapp.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        username = username,
        passwordHash = passwordHash,
        passwordSalt = passwordSalt
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        passwordHash = passwordHash,
        passwordSalt = passwordSalt
    )
}