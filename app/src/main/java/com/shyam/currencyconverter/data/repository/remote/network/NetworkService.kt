package com.shyam.currencyconverter.data.repository.remote.network

import android.content.Context

class NetworkService  constructor(
     private val context: Context,
     private val apiKey: String)// do the initialisation here
{
    fun getRetrofitClient():RetrofitClient{
        return RetrofitClient;
    }

}
