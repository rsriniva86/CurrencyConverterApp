package com.shyam.currencyconverter.di.module

import android.app.Application
import android.content.Context
import com.shyam.currencyconverter.CurrencyConverterApplication
import com.shyam.currencyconverter.data.source.local.CurrencyLocalDataSource
import com.shyam.currencyconverter.data.source.local.database.CurrencyDatabase
import com.shyam.currencyconverter.data.source.remote.CurrencyRemoteDataSource
import com.shyam.currencyconverter.data.source.remote.network.RetrofitClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: CurrencyConverterApplication) {

    @Provides
    @Singleton
    fun providesApplication(): Application = application;

    @Provides
    @Singleton
    fun providesAppContext(): Context = application;

    @Provides
    @Singleton
    fun providesDatabase() :CurrencyDatabase = CurrencyDatabase.invoke(application)

    @Provides
    @Singleton
    fun provideLocalDataSource() :CurrencyLocalDataSource =
        CurrencyLocalDataSource(
            CurrencyDatabase.invoke(application)
        )

    @Provides
    @Singleton
    fun providesRemoteDataSource() :CurrencyRemoteDataSource =
        CurrencyRemoteDataSource(
        RetrofitClient.CURRENCY_LAYER_API_INTERFACE
    )


}