package com.shyam.currencyconverter.data.source

import com.shyam.currencyconverter.data.repository.Result
import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyList
import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyRates

interface CurrencyDataSource {

    suspend fun getCurrencyList(): Result<CurrencyList?>

    suspend fun getCurrencyRates(baseCurrency: String): Result<CurrencyRates?>

    suspend fun insertOrUpdateCurrencyList(currencyList: CurrencyList)

    suspend fun insertOrUpdateCurrencyRates(currencyRates: CurrencyRates)

}