package com.shyam.currencyconverter.di.module

import com.shyam.currencyconverter.data.source.CurrencyDataSource
import com.shyam.currencyconverter.data.source.local.CurrencyLocalDataSource
import com.shyam.currencyconverter.data.source.local.database.CurrencyDatabase
import com.shyam.currencyconverter.data.source.remote.CurrencyRemoteDataSource
import com.shyam.currencyconverter.data.source.remote.network.RetrofitClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class DataSourceModule {

    @Binds
    @Singleton
    @Named("CurrencyLocalDataSource")
    abstract fun provideLocalDataSource(localDataSource: CurrencyLocalDataSource): CurrencyDataSource

    @Binds
    @Singleton
    @Named("CurrencyRemoteDataSource")
    abstract fun providesRemoteDataSource(remoteDataSource: CurrencyRemoteDataSource): CurrencyDataSource


}