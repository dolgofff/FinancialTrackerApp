package com.example.financialtrackerapp.data.mapper

import com.example.financialtrackerapp.data.entity.AimEntity
import com.example.financialtrackerapp.domain.model.Aim

fun AimEntity.toDomain(): Aim {
    return Aim(
        id = id,
        accountId = accountId,
        name = name,
        targetAmount = targetAmount,
        savedAmount = savedAmount
    )
}

fun Aim.toEntity(): AimEntity {
    return AimEntity(
        id = id,
        accountId = accountId,
        name = name,
        targetAmount = targetAmount,
        savedAmount = savedAmount
    )
}