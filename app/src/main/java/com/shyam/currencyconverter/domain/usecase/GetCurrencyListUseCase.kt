package com.shyam.currencyconverter.domain.usecase

import com.shyam.currencyconverter.CurrencyConverterApplication
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepository
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepositoryImpl
import com.shyam.currencyconverter.data.repository.source.local.CurrencyLocalDataSource
import com.shyam.currencyconverter.data.repository.source.local.database.entities.CurrencyList
import com.shyam.currencyconverter.data.repository.source.remote.CurrencyRemoteDataSource
import com.shyam.currencyconverter.data.repository.source.remote.network.RetrofitClient
import com.shyam.currencyconverter.domain.usecase.base.UseCase

class GetCurrencyListUseCase: UseCase<GetCurrencyListUseCase.GetCurrencyListRequest, GetCurrencyListUseCase.GetCurrencyListResponse>() {


     class GetCurrencyListRequest(): UseCase.RequestValues
    data class GetCurrencyListResponse(val output:CurrencyList?): UseCase.ResponseValue

    override suspend fun executeUseCase(requestValues: GetCurrencyListRequest?) {
        val remoteDataSource: CurrencyRemoteDataSource = CurrencyRemoteDataSource(RetrofitClient.CURRENCY_LAYER_API_INTERFACE)
        val localDataSource: CurrencyLocalDataSource? = CurrencyConverterApplication.getDatabase()?.let {
            CurrencyLocalDataSource(
                it
            )
        }
        val repository:CurrencyRatesRepository = CurrencyRatesRepositoryImpl(remoteDataSource = remoteDataSource, localDataSource =localDataSource!! )
        val currencyList = repository.getCurrencyList(true)
        useCaseCallback?.onSuccess(GetCurrencyListResponse(currencyList.data))
    }


}