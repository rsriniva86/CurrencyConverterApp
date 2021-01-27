package com.shyam.currencyconverter.data.repository

import android.util.Log
import com.shyam.currencyconverter.CurrencyConverterApplication
import com.shyam.currencyconverter.data.source.local.CurrencyLocalDataSource
import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyList
import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyRates
import com.shyam.currencyconverter.data.source.remote.CurrencyRemoteDataSource
import com.shyam.currencyconverter.data.source.remote.network.RetrofitClient
import com.shyam.currencyconverter.util.NetworkHelper
import com.shyam.currencyconverter.util.TimestampCalculation


class CurrencyRatesRepositoryImpl(
    private val localDataSource: CurrencyLocalDataSource = CurrencyLocalDataSource(
        CurrencyConverterApplication.getDatabase()
    ),
    private val remoteDataSource: CurrencyRemoteDataSource? = CurrencyRemoteDataSource(
        RetrofitClient.CURRENCY_LAYER_API_INTERFACE
    )

) : CurrencyRatesRepository {

    override suspend fun getCurrencyList(
        forceUpdate: Boolean
    ): Result<CurrencyList?> {

        //get data from local source
        val currencyList = getSavedCurrencyList()

        //Check if it is stale or not
        currencyList.data?.let {
            val isStale = TimestampCalculation.isTimestampStale(it.timestamp)
            if (!isStale) {
                Log.d(TAG, "currencylist is not stale")
                return currencyList
            }
        }
        Log.d(TAG, "currencylist is stale")

        //Handle for no network
        CurrencyConverterApplication.getContext()?.let {
            val isNetworkAvailable = NetworkHelper.isNetworkAvailable(it)
            if (!isNetworkAvailable) {
                Log.d(TAG, "network is not available,sending stale data")
                return getSavedCurrencyList()
            }
        }
        Log.d(TAG, "network is available,fetching data from server")
        try {
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
        forceUpdate: Boolean
    ): Result<CurrencyRates?> {
        val currencyRates = getSavedCurrencyRates(base)
        //Check if it is stale or not
        currencyRates.data?.let {
            val isStale = TimestampCalculation.isTimestampStale(it.timestamp)
            if (!isStale) {
                Log.d(TAG, "currencyrates is not stale")
                return currencyRates
            }
        }
        Log.d(TAG, "currencyrates is  stale")
        //Handle for no network
        CurrencyConverterApplication.getContext()?.let {
            val isNetworkAvailable = NetworkHelper.isNetworkAvailable(it)
            if (!isNetworkAvailable) {
                Log.d(TAG, "network is not available,sending stale data")
                return getSavedCurrencyRates(base)
            }
        }
        Log.d(TAG, "network is available,fetching data from server")
        try {
            val response = remoteDataSource?.getCurrencyRates(base)
            if (response?.status == Result.Status.SUCCESS) {
                response.data?.let {
                    localDataSource.insertOrUpdateCurrencyRates(it)
                }
            } else if (response?.status == Result.Status.ERROR) {
                throw Exception(response.message)
            }
        } catch (ex: Exception) {
            ex.message?.let {
                return Result.error(it, null)
            }
            return Result.error("unknown exception", null)
        }

        return getSavedCurrencyRates(base)
    }

    override suspend fun getSavedCurrencyRates(base: String): Result<CurrencyRates?> {
        return localDataSource.getCurrencyRates(base)
    }


    override suspend fun getSavedCurrencyList(): Result<CurrencyList?> {
        return localDataSource.getCurrencyList()
    }

    companion object {
        val TAG = CurrencyRatesRepositoryImpl::class.simpleName

    }


}