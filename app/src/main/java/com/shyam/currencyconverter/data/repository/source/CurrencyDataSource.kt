package com.shyam.currencyconverter.data.repository.source

import com.shyam.currencyconverter.data.repository.source.local.database.entities.CurrencyRates
import com.shyam.currencyconverter.data.repository.source.local.database.entities.CurrencyList as CurrencyList

interface CurrencyDataSource {
    
    suspend fun getCurrencyList(): Result<CurrencyList?>
    
    suspend fun getCurrencyRates(baseCurrency: String): Result<CurrencyRates?>
    
    suspend fun insertOrUpdateCurrencyList(currencyList: CurrencyList)

    suspend fun insertOrUpdateCurrencyRates(currencyRates: CurrencyRates)
    
}