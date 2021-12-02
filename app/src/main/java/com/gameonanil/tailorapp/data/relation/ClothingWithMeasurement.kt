package com.gameonanil.tailorapp.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.gameonanil.tailorapp.data.entity.Clothing
import com.gameonanil.tailorapp.data.entity.Measurement

data class ClothingWithMeasurement(
    @Embedded
    val clothing: Clothing,
    @Relation(
        parentColumn = "measurementId",
        entityColumn = "measurementId"
    )
    val measurement: Measurement
)
