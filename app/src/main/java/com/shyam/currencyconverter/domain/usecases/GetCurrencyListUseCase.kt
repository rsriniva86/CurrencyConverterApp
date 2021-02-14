package com.shyam.currencyconverter.domain.usecases

import android.util.Log
import com.shyam.currencyconverter.core.DataSourceProviderImpl
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepository
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepositoryImpl
import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyList
import com.shyam.currencyconverter.domain.UseCase
import com.shyam.currencyconverter.util.TimestampCalculation

class GetCurrencyListUseCase :
    UseCase<GetCurrencyListUseCase.GetCurrencyListRequest, GetCurrencyListUseCase.GetCurrencyListResponse>() {


    class GetCurrencyListRequest(val isNetworkConnected: Boolean) : RequestValues
    data class GetCurrencyListResponse(val output: CurrencyList?) : ResponseValue

    override suspend fun executeUseCase(requestValues: GetCurrencyListRequest?) {
        Log.d(CurrencyRatesRepositoryImpl.TAG, "executeUseCase")

        val repository: CurrencyRatesRepository =
            CurrencyRatesRepositoryImpl(dataSourceProvider = DataSourceProviderImpl())
        Log.d(CurrencyRatesRepositoryImpl.TAG, "repository object created")

        //get data from local source
        val savedCurrencyList = repository.getSavedCurrencyList()

        //Check if it is stale or not
        savedCurrencyList.data?.let {
            val isStale = TimestampCalculation.isTimestampStale(it.timestamp)
            if (!isStale) {
                Log.d(CurrencyRatesRepositoryImpl.TAG, "currency list is not stale")
                useCaseCallback?.onSuccess(GetCurrencyListResponse(savedCurrencyList.data))
                return
            }
        }
        Log.d(CurrencyRatesRepositoryImpl.TAG, "currencylist is stale")

        //Handle for no network
        if (requestValues?.isNetworkConnected == false) {
            Log.e(CurrencyRatesRepositoryImpl.TAG, "network is not available,sending stale data")
            useCaseCallback?.onSuccess(GetCurrencyListResponse(savedCurrencyList.data))
            return
        }
        val currencyList = repository.getCurrencyList(true)
        val myMap = currencyList.data?.currencies
        myMap?.forEach {
            Log.d(TAG, "Key is ${it.key} value is ${it.value}")
        }
        useCaseCallback?.onSuccess(GetCurrencyListResponse(currencyList.data))
    }

    companion object {
        private val TAG = GetCurrencyListUseCase::class.simpleName
    }
}