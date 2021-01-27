package com.shyam.currencyconverter.core

import com.shyam.currencyconverter.data.source.local.CurrencyLocalDataSource
import com.shyam.currencyconverter.data.source.remote.CurrencyRemoteDataSource

interface DataSourceProvider {
    fun provideLocalDataSource(): CurrencyLocalDataSource
    fun provideRemoteDataSource(): CurrencyRemoteDataSource
}
