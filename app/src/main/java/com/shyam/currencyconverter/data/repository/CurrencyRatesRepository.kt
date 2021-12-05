package com.shyam.currencyconverter.data.repository

import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyList
import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyRates

interface CurrencyRatesRepository {
    suspend fun getCurrencyRates(base: String, forceUpdate: Boolean,isNetworkConnected: Boolean,currencyMap: Map<String, String>): Result<CurrencyRates?>
    suspend fun getSavedCurrencyRates(base: String): Result<CurrencyRates?>
    suspend fun getCurrencyList(forceUpdate: Boolean,isNetworkConnected:Boolean): Result<CurrencyList?>
    suspend fun getSavedCurrencyList(): Result<CurrencyList?>
}