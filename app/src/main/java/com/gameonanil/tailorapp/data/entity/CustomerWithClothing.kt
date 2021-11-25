package com.gameonanil.tailorapp.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CustomerWithClothing(
    @Embedded
    val customer: Customer,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "customerId"
    )
    val clothing: List<Clothing>
)
