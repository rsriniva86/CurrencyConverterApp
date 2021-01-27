package com.shyam.currencyconverter.data.source.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "CurrencyRates")
data class CurrencyRates(
    @PrimaryKey val base: String,
    val timestamp: Long,
    val rates: Map<String, BigDecimal>
)