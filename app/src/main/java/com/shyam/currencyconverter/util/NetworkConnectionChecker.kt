package com.shyam.currencyconverter.util

import android.content.Context
import javax.inject.Inject

interface NetworkConnectionChecker {
    fun isConnected(): Boolean
}

class NetworkConnectionCheckerImpl @Inject constructor( val context: Context) : NetworkConnectionChecker {
    override fun isConnected(): Boolean {
        return NetworkHelper.isNetworkAvailable(context = context)
    }

}