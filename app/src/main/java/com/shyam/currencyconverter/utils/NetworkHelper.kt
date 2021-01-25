package com.shyam.currencyconverter.utils

import android.content.Context

class NetworkHelper  constructor(
        // Should be Application Context
         private val context: Context) {

    // will check for network connectivity
    fun isNetworkConnected(): Boolean {
        return false
    }
}
