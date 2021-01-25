package com.shyam.currencyconverter.data.repository.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shyam.currencyconverter.data.repository.source.local.database.dao.CurrencyListDao
import com.shyam.currencyconverter.data.repository.source.local.database.dao.CurrencyRatesDao
import com.shyam.currencyconverter.data.repository.source.local.database.entities.CurrencyList
import com.shyam.currencyconverter.data.repository.source.local.database.entities.CurrencyRates
import com.shyam.currencyconverter.data.repository.source.local.database.typeconverters.DoubleMapConverter
import com.shyam.currencyconverter.data.repository.source.local.database.typeconverters.StringMapConverter

@Database(entities = [CurrencyList::class,CurrencyRates::class],version = 1,exportSchema = false)
@TypeConverters(DoubleMapConverter::class,StringMapConverter::class)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun getCurrencyListDao():CurrencyListDao
    abstract fun getCurrencyRatesDao():CurrencyRatesDao

    companion object{
        @Volatile private var instance: CurrencyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK){
                buildDatabase(context)
                    .also { instance = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            CurrencyDatabase::class.java, "currency.db").build()
    }
    var companion = Companion


}