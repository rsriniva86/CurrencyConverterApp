package com.shyam.currencyconverter.di.module

import android.content.Context
import com.shyam.currencyconverter.CurrencyConverterApplication
import com.shyam.currencyconverter.di.component.ApplicationContext
import com.shyam.currencyconverter.di.component.DatabaseInfo
import com.shyam.currencyconverter.di.component.NetworkInfo

import dagger.Module
import dagger.Provides


@Module
class ApplicationModule(private val application: CurrencyConverterApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context = application

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String = "dummy_db"

    @Provides
    @DatabaseInfo
    fun provideDatabaseVersion(): Int = 1

    @Provides
    @NetworkInfo
    fun provideApiKey(): String = "SOME_API_KEY"


}
