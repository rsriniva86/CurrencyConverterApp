package com.shyam.currencyconverter


import android.app.Application
import android.content.Context
import android.util.Log
import com.shyam.currencyconverter.data.source.local.database.CurrencyDatabase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CurrencyConverterApplication : Application() {

    @Inject
    lateinit var currencyDatabase: CurrencyDatabase

    @Inject
    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        application = this;
        currencyDatabase?.let {
            Log.i(TAG, "Database object is injected")
        }

    }



    companion object {
        val TAG: String? = CurrencyConverterApplication::class.simpleName
        private var application: CurrencyConverterApplication? = null
        fun getApplication(): CurrencyConverterApplication? {
            return application;
        }
    }

}