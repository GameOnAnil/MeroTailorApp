package com.gameonanil.tailorapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notificationTable")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val notificationId: Int?,
    val customerId: Int,
    val clothingId: Int

)
