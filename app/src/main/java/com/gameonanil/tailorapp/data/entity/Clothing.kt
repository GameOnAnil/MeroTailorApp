package com.gameonanil.tailorapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clothing_table")
data class Clothing(
    @PrimaryKey(autoGenerate = true)
    val clothingId: Int?,
    val customerId: Int,
    val clothingName: String,
    val price: Float,
    val remaining: Float,
    val dueDate: String
)
