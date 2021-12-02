package com.gameonanil.tailorapp.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Customer

data class CustomerWithClothing(
    @Embedded
    val customer: Customer,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "customerId"
    )
    val clothing: List<Clothing>
)
