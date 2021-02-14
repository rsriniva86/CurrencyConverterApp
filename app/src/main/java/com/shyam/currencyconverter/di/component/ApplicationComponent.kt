package com.shyam.currencyconverter.di.component

import android.app.Application
import com.shyam.currencyconverter.CurrencyConverterApplication
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepository
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepositoryImpl
import com.shyam.currencyconverter.data.source.local.database.CurrencyDatabase
import com.shyam.currencyconverter.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: CurrencyConverterApplication)
    fun inject(repository: CurrencyRatesRepositoryImpl)
    fun getDatabase():CurrencyDatabase
    fun getApplication():Application
}