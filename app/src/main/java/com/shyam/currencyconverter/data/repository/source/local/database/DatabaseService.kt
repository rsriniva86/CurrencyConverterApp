package com.shyam.currencyconverter.data.repository.source.local.database

import android.content.Context

import com.shyam.currencyconverter.di.component.ApplicationContext
import com.shyam.currencyconverter.di.component.DatabaseInfo

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseService @Inject constructor(
    @ApplicationContext private val context: Context,
    @DatabaseInfo private val databaseName: String,
    @DatabaseInfo private val version: Int)// do the initialisation here
{

    fun getCurrencyDatabase():CurrencyDatabase{
        return CurrencyDatabase_Impl()
    }

}
