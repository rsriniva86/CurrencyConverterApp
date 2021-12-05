package com.shyam.currencyconverter.data.repository

import android.util.Log
import com.shyam.currencyconverter.data.source.CurrencyDataSource
import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyList
import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyRates
import com.shyam.currencyconverter.util.TimestampCalculation
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Named
import com.shyam.currencyconverter.data.repository.Result.Companion.success



class CurrencyRatesRepositoryImpl @Inject constructor(
    @Named("CurrencyLocalDataSource")
    private val localDataSource: CurrencyDataSource,
    @Named ("CurrencyRemoteDataSource")
    private val remoteDataSource: CurrencyDataSource
): CurrencyRatesRepository {

    init {
        Log.d(TAG, "init")

        localDataSource?.let {
            Log.d(TAG, "localDataSource is not null")
        }
        remoteDataSource?.let {
            Log.d(TAG, "remoteDataSource is not null")
        }
    }


    override suspend fun getCurrencyList(forceUpdate: Boolean,isNetworkConnected:Boolean): Result<CurrencyList?> {

        try {
            //get data from local source
            val savedCurrencyList =getSavedCurrencyList()

            //Check if it is stale or not
            savedCurrencyList.data?.let {
                val isStale = TimestampCalculation.isTimestampStale(it.timestamp)
                if (!isStale) {
                    Log.d(TAG, "currency list is not stale")
                    return savedCurrencyList
                }
            }
            Log.d(TAG, "currencylist is stale")

            //Handle for no network
            if (!isNetworkConnected) {
                Log.e(TAG, "network is not available,sending stale data")
                return savedCurrencyList
            }

            val response = remoteDataSource?.getCurrencyList()
            if (response?.status == Result.Status.SUCCESS) {
                val myMap = response.data?.currencies
                myMap?.forEach {
                    Log.d(TAG, "CurrencyRatesRepositoryImpl::Key is ${it.key} value is ${it.value}")
                }

                response.data?.let {
                    Log.d(TAG, "insertOrUpdateCurrencyList")
                    localDataSource.insertOrUpdateCurrencyList(it)
                    Log.d(TAG, "insertOrUpdateCurrencyList done...")
                }
                return response
            } else if (response?.status == Result.Status.ERROR) {
                throw Exception(response.message)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            ex.message?.let {
                return Result.error(it, null)
            }
            return Result.error("unknown exception", null)
        }

        return getSavedCurrencyList()
    }

    override suspend fun getCurrencyRates(
        base: String,
        forceUpdate: Boolean,
        isNetworkConnected: Boolean,
        currencyMap: Map<String, String>
    ): Result<CurrencyRates?> {
        try {
            val savedCurrencyRates = getSavedCurrencyRates(BASE_CURRENCY_INTERNAL)

            //Check if it is stale or not
            savedCurrencyRates.data?.let {
                val isStale = TimestampCalculation.isTimestampStale(it.timestamp)
                if (!isStale) {
                    Log.d(TAG, "currency rates is not stale")
                    return convertAsPerBase(savedCurrencyRates,base,currencyMap)
                }
            }
            Log.d(TAG, "currency rates is  stale")
            //Handle for no network
            if (!isNetworkConnected) {
                Log.d(
                    TAG,
                    "network is not available,sending stale data"
                )
                return convertAsPerBase(savedCurrencyRates,base,currencyMap)
            }

            Log.d(TAG, "network is available,fetching data from server")

            val response = remoteDataSource.getCurrencyRates(BASE_CURRENCY_INTERNAL)
            if (response.status == Result.Status.SUCCESS) {
                response.data?.let {
                    localDataSource.insertOrUpdateCurrencyRates(it)
                }
            } else if (response.status == Result.Status.ERROR) {
                throw Exception(response.message)
            }
        } catch (ex: Exception) {
            ex.message?.let {
                return Result.error(it, null)
            }
            return Result.error("unknown exception", null)
        }

        return convertAsPerBase(getSavedCurrencyRates(BASE_CURRENCY_INTERNAL),base,currencyMap)
    }

    private fun convertAsPerBase(savedCurrencyRates: Result<CurrencyRates?>,
                                 baseCurrency: String,
                                 currencyMap: Map<String, String>
    ): Result<CurrencyRates?> {
        if (baseCurrency == BASE_CURRENCY_INTERNAL) {
            savedCurrencyRates
        } else {
            getConversionMap(
                userCurrency = baseCurrency,
                currentRates = savedCurrencyRates,
                currencyMap =currencyMap
            )

        }
    }
    private fun getConversionMap(
        userCurrency: String,
        currentRates: Result<CurrencyRates?>,
        currencyMap: Map<String, String>
    ): Result<CurrencyRates?> {

        val outputMap = mutableMapOf<String, BigDecimal>()
        //TODO error handling
        val userCurrencyValue: BigDecimal =
            currentRates.data?.rates!![BASE_CURRENCY_INTERNAL + userCurrency] ?: error("cannot get userCurrencyValue")
        for (currentCurrency in currencyMap.keys) {
            val key: String = BASE_CURRENCY_INTERNAL + currentCurrency
            val baseCurrencyConversion: BigDecimal = currentRates.data.rates[key] as BigDecimal
            val updatedValue: BigDecimal = (baseCurrencyConversion / userCurrencyValue)
            outputMap[userCurrency + currentCurrency] = updatedValue
        }
        currentRates.data.rates=outputMap
        return currentRates
    }

    override suspend fun getSavedCurrencyRates(base: String): Result<CurrencyRates?> {
        return localDataSource.getCurrencyRates(base)
    }


    override suspend fun getSavedCurrencyList(): Result<CurrencyList?> {
        return localDataSource.getCurrencyList()
    }

    companion object {
        private const val BASE_CURRENCY_INTERNAL = "USD"
        val TAG = CurrencyRatesRepositoryImpl::class.simpleName
    }


}