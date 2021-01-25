package com.shyam.currencyconverter.domain.usecase

import androidx.room.RoomDatabase
import com.shyam.currencyconverter.CurrencyConverterApplication
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepository
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepositoryImpl
import com.shyam.currencyconverter.data.repository.source.local.CurrencyLocalDataSource
import com.shyam.currencyconverter.data.repository.source.remote.CurrencyRemoteDataSource
import com.shyam.currencyconverter.data.repository.source.remote.network.RetrofitClient
import com.shyam.currencyconverter.domain.usecase.base.UseCase

class ConvertCurrencyUseCase : UseCase<ConvertCurrencyUseCase.ConvertCurrencyRequest, ConvertCurrencyUseCase.ConvertCurrencyResponse>() {


    data class ConvertCurrencyRequest(val baseCurrency:String): UseCase.RequestValues
    data class ConvertCurrencyResponse(val output:Map<String,Double>): UseCase.ResponseValue

    override suspend fun executeUseCase(requestValues: ConvertCurrencyRequest?) {

        val remoteDataSource:CurrencyRemoteDataSource = CurrencyRemoteDataSource(RetrofitClient.CURRENCY_LAYER_API_INTERFACE)
        val localDataSource: CurrencyLocalDataSource? = CurrencyConverterApplication.getDatabase()?.let {
            CurrencyLocalDataSource(
                it
            )
        }
        val repository:CurrencyRatesRepository = CurrencyRatesRepositoryImpl(remoteDataSource = remoteDataSource, localDataSource =localDataSource!! )
        val currencyList = repository.getCurrencyList(true)

    }
}