package com.shyam.currencyconverter.data.source.remote.network

import com.shyam.currencyconverter.data.source.remote.network.response.GetCurrenctListResponse
import com.shyam.currencyconverter.data.source.remote.network.response.GetCurrencyRatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyLayerApiInterface {

    @GET("list")
    suspend fun getCurrencyList(@Query ("access_key") access_key:String) : Response<GetCurrenctListResponse>

    @GET("live")
    suspend fun getCurrencyRates(@Query ("access_key") access_key:String,
                                 @Query("source") source:String)
            : Response<GetCurrencyRatesResponse>

    companion object{
        const val BASE_URL="http://api.currencylayer.com"
    }
}