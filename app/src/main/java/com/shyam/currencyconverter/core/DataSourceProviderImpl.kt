package com.shyam.currencyconverter.core

import com.shyam.currencyconverter.CurrencyConverterApplication
import com.shyam.currencyconverter.data.source.local.CurrencyLocalDataSource
import com.shyam.currencyconverter.data.source.remote.CurrencyRemoteDataSource
import com.shyam.currencyconverter.data.source.remote.network.RetrofitClient

class DataSourceProviderImpl : DataSourceProvider {
    override fun provideLocalDataSource(): CurrencyLocalDataSource =
        CurrencyLocalDataSource(null)

    override fun provideRemoteDataSource(): CurrencyRemoteDataSource =
        CurrencyRemoteDataSource(
            RetrofitClient.CURRENCY_LAYER_API_INTERFACE
        )


}