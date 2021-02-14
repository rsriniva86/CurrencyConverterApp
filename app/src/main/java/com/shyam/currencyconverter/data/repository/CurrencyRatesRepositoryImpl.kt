package com.shyam.currencyconverter.data.repository

import android.util.Log
import com.shyam.currencyconverter.CurrencyConverterApplication
import com.shyam.currencyconverter.data.source.local.CurrencyLocalDataSource
import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyList
import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyRates
import com.shyam.currencyconverter.data.source.remote.CurrencyRemoteDataSource
import javax.inject.Inject


class CurrencyRatesRepositoryImpl : CurrencyRatesRepository {


    @Inject
    lateinit var localDataSource: CurrencyLocalDataSource;

    @Inject
    lateinit var remoteDataSource: CurrencyRemoteDataSource;

    init {
        Log.d(TAG, "init")
        CurrencyConverterApplication.getApplication()?.let {
            Log.d(TAG, "Injection code")
            it.applicationComponent.inject(this)
        }
        localDataSource?.let {
            Log.d(TAG, "localDataSource is not null")
        }
        remoteDataSource?.let {
            Log.d(TAG, "remoteDataSource is not null")
        }
    }


    override suspend fun getCurrencyList(forceUpdate: Boolean): Result<CurrencyList?> {

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