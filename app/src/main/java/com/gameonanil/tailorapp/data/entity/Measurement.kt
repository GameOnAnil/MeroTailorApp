package com.gameonanil.tailorapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurement_table")
data class Measurement(
    @PrimaryKey(autoGenerate = true)
    val measureId: Int?,
    val customerId: Int,
    val chati: Int,
    val kum: Int,
    val baulaLambai: Int,
    val kamarLambai: Int,
    val puraLambai: Int,
    val kafGhera: Int,
    val kakhi: Int,
    val kamarGhera: Int,
)
