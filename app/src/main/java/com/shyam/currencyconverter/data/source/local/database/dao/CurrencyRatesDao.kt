package com.shyam.currencyconverter.data.source.local.database.dao

import androidx.room.*
import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyRates

@Dao
interface CurrencyRatesDao {

    @Query("Select * from CurrencyRates where base = :symbol")
    fun getRates(symbol:String):CurrencyRates?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateCurrencyRates(currencyRates: CurrencyRates)

}