package com.shyam.currencyconverter.data.repository.source.local

import com.shyam.currencyconverter.data.repository.source.local.database.CurrencyDatabase
import com.shyam.currencyconverter.data.repository.source.local.database.entities.CurrencyList
import com.shyam.currencyconverter.data.repository.source.local.database.entities.CurrencyRates
import com.shyam.currencyconverter.data.repository.source.CurrencyDataSource
import com.shyam.currencyconverter.data.repository.source.Result
import com.shyam.currencyconverter.data.repository.source.Result.Companion.success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class CurrencyLocalDataSource  constructor(private val appDatabase: CurrencyDatabase,
                              private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): CurrencyDataSource {

    override suspend fun getCurrencyList(): Result<CurrencyList?> {
        return success(appDatabase.getCurrencyListDao().getCurrencyList())
    }

    override suspend fun getCurrencyRates(baseCurrency: String): Result<CurrencyRates?> {
        return success(appDatabase.getCurrencyRatesDao().getRates(baseCurrency))
    }

    override suspend fun insertOrUpdateCurrencyList(currencyList: CurrencyList) {
        appDatabase.getCurrencyListDao().insertOrUpdateCurrencyList(currencyList)
    }

    override suspend fun insertOrUpdateCurrencyRates(currencyRates: CurrencyRates) {
        appDatabase.getCurrencyRatesDao().insertOrUpdateCurrencyRates(currencyRates)
    }

}