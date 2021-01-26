package com.shyam.currencyconverter.data.source.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CurrencyList")
data class CurrencyList(@PrimaryKey(autoGenerate = true) val id:Int,val timestamp:Long, val currencies:Map<String,String>?)