package com.example.financialtrackerapp.data.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.financialtrackerapp.data.entity.AccountEntity
import com.example.financialtrackerapp.data.entity.AccountUserCrossRef
import com.example.financialtrackerapp.data.entity.UserEntity

data class UserWithAccounts(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(AccountUserCrossRef::class)
    )
    val accounts: List<AccountEntity>
)
