package com.gameonanil.tailorapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer_table")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val customerId: Int,
    val customerName: String
)
