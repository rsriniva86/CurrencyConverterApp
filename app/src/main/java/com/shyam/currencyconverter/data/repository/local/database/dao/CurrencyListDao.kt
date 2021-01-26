package com.shyam.currencyconverter.data.repository.local.database.dao

import androidx.room.*
import com.shyam.currencyconverter.data.repository.local.database.entities.CurrencyList

@Dao
interface CurrencyListDao {

    @Query("Select * from CurrencyList")
    fun getCurrencyList():CurrencyList?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateCurrencyList(currency: CurrencyList)

}