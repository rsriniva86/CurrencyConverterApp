package com.shyam.currencyconverter.domain.usecases

import android.util.Log
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepository
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepositoryImpl
import com.shyam.currencyconverter.domain.UseCase
import java.math.BigDecimal

class ConvertCurrencyUseCase :
    UseCase<ConvertCurrencyUseCase.ConvertCurrencyRequest, ConvertCurrencyUseCase.ConvertCurrencyResponse>() {


    data class ConvertCurrencyRequest(
        val baseCurrency: String,
        val currencyMap: Map<String, String>
    ) : UseCase.RequestValues

    data class ConvertCurrencyResponse(val output: Map<String, BigDecimal>) : UseCase.ResponseValue

    override suspend fun executeUseCase(requestValues: ConvertCurrencyRequest?) {
        val repository: CurrencyRatesRepository = CurrencyRatesRepositoryImpl()
        val currencyRates = repository.getCurrencyRates(base = "USD", forceUpdate = true)
        val myMap = currencyRates.data?.rates
        myMap?.forEach {
            Log.d(TAG, "ConvertCurrencyUseCase::Key is ${it.key} value is ${it.value}")
        }
        currencyRates.data?.rates?.let { ConvertCurrencyResponse(it) }?.let {
            useCaseCallback?.onSuccess(
                if (requestValues?.baseCurrency == "USD") {
                    it
                } else {
                    val userCurrency = requestValues?.baseCurrency
                    getConversionMap(
                        userCurrency = userCurrency as String,
                        baseMap = it.output,
                        currencyMap = requestValues.currencyMap
                    )

                }
            )
        }
    }


    fun getConversionMap(
        userCurrency: String,
        baseMap: Map<String, BigDecimal>,
        currencyMap: Map<String, String>
    ): ConvertCurrencyResponse {


        val outputMap = mutableMapOf<String, BigDecimal>()

        //1 USD= .5 SGD
        val userCurrencyValue: BigDecimal = baseMap.get("USD" + userCurrency)!!

        //1 USD =100 Rs
        //1 SGD = ? 100/1.5
        for (currentCurrency in currencyMap.keys) {
            var key: String = "USD" + currentCurrency
            var baseCurrencyConversion: BigDecimal = baseMap.get(key) as BigDecimal
            var newvalue: BigDecimal = (baseCurrencyConversion / userCurrencyValue)
            outputMap.put(userCurrency + currentCurrency, newvalue)

        }
        return ConvertCurrencyResponse(outputMap)
    }

    companion object {
        val TAG = ConvertCurrencyUseCase::class.simpleName
    }

}