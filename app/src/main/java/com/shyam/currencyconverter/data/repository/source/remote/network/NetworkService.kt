package com.shyam.currencyconverter.data.repository.source.remote.network

import android.content.Context

import com.shyam.currencyconverter.di.component.ApplicationContext
import com.shyam.currencyconverter.di.component.NetworkInfo

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkService @Inject constructor(
    @ApplicationContext private val context: Context,
    @NetworkInfo private val apiKey: String)// do the initialisation here
{
    fun getRetrofitClient():RetrofitClient{
        return RetrofitClient;
    }

}
