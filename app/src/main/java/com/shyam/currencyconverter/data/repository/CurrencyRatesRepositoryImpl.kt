package com.shyam.currencyconverter.data.repository

import com.shyam.currencyconverter.CurrencyConverterApplication
import com.shyam.currencyconverter.data.repository.local.CurrencyLocalDataSource
import com.shyam.currencyconverter.data.repository.local.database.entities.CurrencyList
import com.shyam.currencyconverter.data.repository.local.database.entities.CurrencyRates
import com.shyam.currencyconverter.data.repository.remote.CurrencyRemoteDataSource
import com.shyam.currencyconverter.data.repository.remote.network.RetrofitClient
import java.lang.Exception


class CurrencyRatesRepositoryImpl(
    private val localDataSource: CurrencyLocalDataSource = CurrencyLocalDataSource(CurrencyConverterApplication.getDatabase()),
    private val remoteDataSource: CurrencyRemoteDataSource? = CurrencyRemoteDataSource(RetrofitClient.CURRENCY_LAYER_API_INTERFACE)

): CurrencyRatesRepository {





    override suspend fun getCurrencyRates(
        base: String,
        forceUpdate: Boolean
    ): Result<CurrencyRates?> {
        try {
            val response = remoteDataSource?.getCurrencyRates(base)
            if (response?.status == Result.Status.SUCCESS){
                response?.data?.let {
                    localDataSource.insertOrUpdateCurrencyRates(it)
                }
            }else if (response?.status == Result.Status.ERROR){
                throw Exception(response?.message)
            }
        }catch (ex: Exception){
            ex.message?.let{
                return Result.error(it,null)
            }
            return Result.error("unknown exception",null)
        }

        return getSavedCurrencyRates(base)
    }

    override suspend fun getSavedCurrencyRates(base: String): Result<CurrencyRates?> {
        return localDataSource.getCurrencyRates(base)
    }

    override suspend fun getCurrencyList(
        forceUpdate: Boolean
    ): Result<CurrencyList?> {
        try {
            val response = remoteDataSource?.getCurrencyList()
            if (response?.status == Result.Status.SUCCESS){
                val myMap=response?.data?.currencies
                myMap?.forEach {
                    System.out.println("CurrencyRatesRepositoryImpl::Key is ${it.key} value is ${it.value}")
                }

                response?.data?.let {
                    System.out.println("insertOrUpdateCurrencyList")
                    localDataSource.insertOrUpdateCurrencyList(it)
                    System.out.println("insertOrUpdateCurrencyList done...")
                }
                return response!!
            }else if (response?.status == Result.Status.ERROR){
                throw Exception(response.message)
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            ex.message?.let{
                return Result.error(it,null)
            }
            return Result.error("unknown exception",null)
        }

        return getSavedCurrencyList()
    }

    override suspend fun getSavedCurrencyList(): Result<CurrencyList?> {
        return localDataSource.getCurrencyList()
    }




}