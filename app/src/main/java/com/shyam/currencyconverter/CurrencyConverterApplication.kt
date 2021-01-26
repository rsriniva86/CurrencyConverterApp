package com.shyam.currencyconverter


import android.app.Application
import com.shyam.currencyconverter.data.source.local.database.CurrencyDatabase


class CurrencyConverterApplication : Application() {

    companion object {
        var databaseService: CurrencyDatabase?=null
        fun getDatabase(): CurrencyDatabase? {
            return databaseService
        }

    }


    override fun onCreate() {
        super.onCreate()
        getDependencies()
    }

    private fun getDependencies() {
        databaseService= CurrencyDatabase.invoke(this)
    }
}