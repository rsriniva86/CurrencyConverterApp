package com.shyam.currencyconverter.data.source.local.database.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.math.BigDecimal

object BigDecimalMapConverter {
    @TypeConverter
    @JvmStatic
    fun fromString(value: String?): Map<String, BigDecimal> {
        val mapType = object :
            TypeToken<Map<String?, BigDecimal?>?>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    @JvmStatic
    fun fromBigDecimalMap(map: Map<String?, BigDecimal?>?): String {
        val gson = Gson()
        return gson.toJson(map)
    }
}