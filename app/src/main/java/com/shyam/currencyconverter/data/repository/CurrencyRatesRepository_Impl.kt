package com.shyam.currencyconverter.data.repository

import com.shyam.currencyconverter.data.repository.source.Result
import com.shyam.currencyconverter.data.repository.source.local.CurrencyLocalDataSource
import com.shyam.currencyconverter.data.repository.source.local.database.entities.CurrencyList
import com.shyam.currencyconverter.data.repository.source.local.database.entities.CurrencyRates
import com.shyam.currencyconverter.data.repository.source.remote.CurrencyRemoteDataSource
import java.lang.Exception

class CurrencyRatesRepository_Impl(
    private val localDataSource: CurrencyLocalDataSource,
    private val remoteDataSource: CurrencyRemoteDataSource
): CurrencyRatesRepository {



//    fun getConversionMap():HashMap<String,Double>{
//        val output=hashMapOf<String,Double>()
//
//        val currencyList=Currency.values()
//        for (currentCurrency in currencyList){
//            if(baseCurrency.name == currentCurrency.name){
//
//            }else {
//                output.put(baseCurrency.name + currentCurrency.name, Math.random())
//            }
//        }
//        return output;
//    }
//
//    fun getConversionMap(userCurrency:Currency, baseMap:HashMap<String,Double>):HashMap<String,Double>{
//        val output=hashMapOf<String,Double>()
//        val currencyList=Currency.values()
//
//        //1 USD= .5 SGD
//        val userCurrencyValue:Double=baseMap.get(baseCurrency.name+userCurrency)!!
//
//        //1 USD =100 Rs
//        //1 SGD = ? 100/1.5
//        for (currentCurrency in currencyList){
//            if(userCurrency.name == currentCurrency.name){
//
//            }else {
//                var key:String=baseCurrency.name+currentCurrency.name
//                var baseCurrencyConversion:Double=baseMap.get(key) as Double
//                var newvalue:Double= (baseCurrencyConversion / userCurrencyValue)
//                output.put(userCurrency.name + currentCurrency.name,newvalue )
//            }
//        }
//        return output;
//    }

    override suspend fun getCurrencyRates(
        base: String,
        forceUpdate: Boolean
    ): Result<CurrencyRates?> {
        try {
            val response = remoteDataSource.getCurrencyRates(base)
            if (response.status == Result.Status.SUCCESS){
                response.data?.let {
                    localDataSource.insertOrUpdateCurrencyRates(it)
                }
            }else if (response.status == Result.Status.ERROR){
                throw Exception(response.message)
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
            val response = remoteDataSource.getCurrencyList()
            if (response.status == Result.Status.SUCCESS){
                response.data?.let {
                    localDataSource.insertOrUpdateCurrencyList(it)
                }
            }else if (response.status == Result.Status.ERROR){
                throw Exception(response.message)
            }
        }catch (ex: Exception){
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