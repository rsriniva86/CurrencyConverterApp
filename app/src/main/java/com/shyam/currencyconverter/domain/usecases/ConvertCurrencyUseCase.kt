package com.shyam.currencyconverter.domain.usecases

import android.util.Log
import com.shyam.currencyconverter.CurrencyConverterApplication
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepository
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepositoryImpl
import com.shyam.currencyconverter.data.repository.Result
import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyRates
import com.shyam.currencyconverter.domain.UseCase
import com.shyam.currencyconverter.util.TimestampCalculation
import java.math.BigDecimal
import javax.inject.Inject

class ConvertCurrencyUseCase :
    UseCase<ConvertCurrencyUseCase.ConvertCurrencyRequest, ConvertCurrencyUseCase.ConvertCurrencyResponse>() {
    @Inject
    lateinit var repository: CurrencyRatesRepository

    init {
        CurrencyConverterApplication.getApplication()?.let {
            it.applicationComponent.inject(this);
        }
    }

    data class ConvertCurrencyRequest(
        val baseCurrency: String,
        val currencyMap: Map<String, String>,
        val isNetworkConnected: Boolean
    ) : RequestValues

    data class ConvertCurrencyResponse(val output: Map<String, BigDecimal>) : ResponseValue

    override suspend fun executeUseCase(requestValues: ConvertCurrencyRequest?) {

        val savedCurrencyRates = repository.getSavedCurrencyRates(BASE_CURRENCY_INTERNAL)

        //Check if it is stale or not
        savedCurrencyRates.data?.let {
            val isStale = TimestampCalculation.isTimestampStale(it.timestamp)
            if (!isStale) {
                Log.d(CurrencyRatesRepositoryImpl.TAG, "currency rates is not stale")
                onSuccess(requestValues, savedCurrencyRates)
                return
            }
        }
        Log.d(CurrencyRatesRepositoryImpl.TAG, "currency rates is  stale")
        //Handle for no network
        if (requestValues?.isNetworkConnected == false) {
            Log.d(
                CurrencyRatesRepositoryImpl.TAG,
                "network is not available,sending stale data"
            )
            onSuccess(requestValues, savedCurrencyRates)
            return
        }

        Log.d(CurrencyRatesRepositoryImpl.TAG, "network is available,fetching data from server")

        val currencyRates =
            repository.getCurrencyRates(base = BASE_CURRENCY_INTERNAL, forceUpdate = true)
        val myMap = currencyRates.data?.rates
        myMap?.forEach {
            Log.d(TAG, "ConvertCurrencyUseCase::Key is ${it.key} value is ${it.value}")
        }
        onSuccess(requestValues, currencyRates)
    }

    private fun onSuccess(
        requestValues: ConvertCurrencyRequest?,
        currencyRates: Result<CurrencyRates?>
    ) {
        currencyRates.data?.rates?.let { ConvertCurrencyResponse(it) }?.let {
            useCaseCallback?.onSuccess(
                if (requestValues?.baseCurrency == BASE_CURRENCY_INTERNAL) {
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


    private fun getConversionMap(
        userCurrency: String,
        baseMap: Map<String, BigDecimal>,
        currencyMap: Map<String, String>
    ): ConvertCurrencyResponse {

        val outputMap = mutableMapOf<String, BigDecimal>()
        //TODO error handling
        val userCurrencyValue: BigDecimal =
            baseMap[BASE_CURRENCY_INTERNAL + userCurrency] ?: error("cannot get userCurrencyValue")
        for (currentCurrency in currencyMap.keys) {
            val key: String = BASE_CURRENCY_INTERNAL + currentCurrency
            val baseCurrencyConversion: BigDecimal = baseMap[key] as BigDecimal
            val updatedValue: BigDecimal = (baseCurrencyConversion / userCurrencyValue)
            outputMap[userCurrency + currentCurrency] = updatedValue
        }
        return ConvertCurrencyResponse(outputMap)
    }

    companion object {
        private const val BASE_CURRENCY_INTERNAL = "USD"
        val TAG = ConvertCurrencyUseCase::class.simpleName
    }

}