package com.shyam.currencyconverter


import android.app.Application
import android.content.Context
import com.shyam.currencyconverter.data.source.local.database.CurrencyDatabase


class CurrencyConverterApplication : Application() {

    companion object {
        var databaseService: CurrencyDatabase? = null
        fun getDatabase(): CurrencyDatabase? {
            return databaseService
        }

        var currencyConverterApplicationContext: Context? = null
        fun getContext(): Context? {
            return currencyConverterApplicationContext
        }

    }


    override fun onCreate() {
        super.onCreate()
        getDependencies()
    }

    private fun getDependencies() {
        databaseService = CurrencyDatabase.invoke(this)
        currencyConverterApplicationContext = applicationContext
    }
}