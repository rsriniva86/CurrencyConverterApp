package com.shyam.currencyconverter


import android.app.Application
import android.content.Context
import android.util.Log
import com.shyam.currencyconverter.data.source.local.database.CurrencyDatabase
import com.shyam.currencyconverter.di.component.ApplicationComponent
import com.shyam.currencyconverter.di.component.DaggerApplicationComponent
import com.shyam.currencyconverter.di.module.ApplicationModule
import javax.inject.Inject


class CurrencyConverterApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var currencyDatabase: CurrencyDatabase

    @Inject
    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        application = this;
        injectDependencies()

        currencyDatabase?.let {
            Log.i(TAG, "Database object is injected")
        }

    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

    companion object {
        val TAG: String? = CurrencyConverterApplication::class.simpleName
        private var application: CurrencyConverterApplication? = null
        fun getApplication(): CurrencyConverterApplication? {
            return application;
        }
    }

}