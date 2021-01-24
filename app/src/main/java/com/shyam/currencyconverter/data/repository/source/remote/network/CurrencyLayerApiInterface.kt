package com.shyam.currencyconverter.data.repository.source.remote.network

import com.shyam.currencyconverter.data.repository.source.local.database.entities.CurrencyList
import com.shyam.currencyconverter.data.repository.source.local.database.entities.CurrencyRates
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyLayerApiInterface {

    @GET("list")
    suspend fun getCurrencyList(@Query ("access_key") access_key:String) : Response<CurrencyList>

    @GET("live")
    suspend fun getCurrencyRates(@Query ("access_key") access_key:String,
                                 @Query("source") source:String)
            : Response<CurrencyRates>

    companion object{
        const val BASE_URL="https://api.currencylayer.com"
    }
}