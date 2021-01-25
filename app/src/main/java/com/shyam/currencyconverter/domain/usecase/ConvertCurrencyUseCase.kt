package com.shyam.currencyconverter.domain.usecase

import com.shyam.currencyconverter.data.repository.CurrencyRatesRepository
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepositoryImpl
import com.shyam.currencyconverter.domain.usecase.base.UseCase

class ConvertCurrencyUseCase : UseCase<ConvertCurrencyUseCase.ConvertCurrencyRequest, ConvertCurrencyUseCase.ConvertCurrencyResponse>() {


    data class ConvertCurrencyRequest(val baseCurrency:String): UseCase.RequestValues
    data class ConvertCurrencyResponse(val output:Map<String,Double>): UseCase.ResponseValue

    override suspend fun executeUseCase(requestValues: ConvertCurrencyRequest?) {
        val repository:CurrencyRatesRepository = CurrencyRatesRepositoryImpl()
        val currencyRates = repository.getCurrencyRates(base = requestValues?.baseCurrency as String,forceUpdate = true)
        val myMap=currencyRates.data?.rates
        myMap?.forEach {
            System.out.println("ConvertCurrencyUseCase::Key is ${it.key} value is ${it.value}")
        }
        currencyRates.data?.rates?.let { ConvertCurrencyResponse(it) }?.let {
            useCaseCallback?.onSuccess(
                it
            )
        }
    }
}