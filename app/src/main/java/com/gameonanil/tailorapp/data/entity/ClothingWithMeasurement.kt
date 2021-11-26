package com.gameonanil.tailorapp.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ClothingWithMeasurement(
    @Embedded
    val clothing: Clothing,
    @Relation(
        parentColumn = "measurementId",
        entityColumn = "measurementId"
    )
    val measurement: Measurement
)
