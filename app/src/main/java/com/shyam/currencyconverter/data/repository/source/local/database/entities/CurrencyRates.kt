package com.shyam.currencyconverter.data.repository.source.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CurrencyRates")
data class CurrencyRates(@PrimaryKey val base: String, val timestamp: Long, val rates: Map<String, Double>)