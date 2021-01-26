package com.shyam.currencyconverter.data.source.remote

import android.util.Log
import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyList
import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyRates
import com.shyam.currencyconverter.data.source.remote.network.CurrencyLayerApiInterface
import com.shyam.currencyconverter.data.source.CurrencyDataSource
import com.shyam.currencyconverter.data.repository.Result
import com.shyam.currencyconverter.data.repository.Result.Companion.error
import com.shyam.currencyconverter.data.repository.Result.Companion.success

class CurrencyRemoteDataSource (private val currencyLayerApiInterface: CurrencyLayerApiInterface):
    CurrencyDataSource {



    override suspend fun getCurrencyList(): Result<CurrencyList?> {
        return try {
            val response = currencyLayerApiInterface.getCurrencyList(access_key)
            if (response.isSuccessful) {
                val responsebody = success(response.body())
                val responsebody2=responsebody.data
                val myMap=responsebody2?.currencies
                myMap?.forEach {
                    Log.d(TAG,"Key is ${it.key} value is ${it.value}")
                }
                Result(
                    Result.Status.SUCCESS,
                    CurrencyList(1, 1222, responsebody2?.currencies),
                    ""
                )

            } else {
                error("Something went wrong ")
            }
        } catch (e: Exception) {
           error(e.message ?: "exception")
        }
    }

    override suspend fun getCurrencyRates(baseCurrency: String): Result<CurrencyRates?> {
        return try {
            val response = currencyLayerApiInterface.getCurrencyRates(access_key,baseCurrency)
            if (response.isSuccessful) {
                val responsebody = success(response.body())
                val responsebody2=responsebody.data
                val myMap=responsebody2?.currencies
                myMap?.forEach {
                    Log.d(TAG,"getCurrencyRates::Key is ${it.key} value is ${it.value}")
                }
                Result(
                    Result.Status.SUCCESS,
                    responsebody2?.currencies?.let { CurrencyRates(baseCurrency, 123, it) },
                    ""
                )

            } else {
                error("Something went wrong ")
            }
        } catch (e: Exception) {
            error(e.message ?: "exception")
        }
    }

    override suspend fun insertOrUpdateCurrencyList(currencyList: CurrencyList) {
    }

    override suspend fun insertOrUpdateCurrencyRates(currencyRates: CurrencyRates) {

    }

    companion object {
        val TAG= CurrencyRemoteDataSource::class.simpleName
        const val access_key="78df2123bfe0efed327a5a0ea29c9764"
    }

}