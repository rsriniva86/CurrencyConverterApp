package com.shyam.currencyconverter.util

import android.content.Context

interface NetworkConnectionChecker {
    fun isConnected(): Boolean
}

class NetworkConnectionCheckerImpl(val context: Context) : NetworkConnectionChecker {
    override fun isConnected(): Boolean {
        return NetworkHelper.isNetworkAvailable(context = context)
    }

}