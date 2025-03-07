package com.example.financialtrackerapp.data.mapper

import com.example.financialtrackerapp.data.entity.AdviceEntity
import com.example.financialtrackerapp.domain.model.Advice
import com.example.financialtrackerapp.domain.model.enums.AdviceType

fun AdviceEntity.toDomain(): Advice {
    return Advice(
        id = id,
        accountId = accountId,
        type = AdviceType.valueOf(type),
        content = content
    )
}

fun Advice.toEntity(): AdviceEntity {
    return AdviceEntity(
        id = id,
        accountId = accountId,
        type = type.name,
        content = content
    )
}
