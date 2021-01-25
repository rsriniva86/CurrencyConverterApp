package com.shyam.currencyconverter.data.repository.source.local.database

import android.content.Context


class DatabaseService  constructor(
     private val context: Context,
     private val databaseName: String,
     private val version: Int)// do the initialisation here
 {

    fun getCurrencyDatabase():CurrencyDatabase{
        return CurrencyDatabase_Impl()
    }

}
