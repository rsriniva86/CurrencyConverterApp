package com.shyam.currencyconverter.utils

import android.content.Context
import com.shyam.currencyconverter.di.component.ApplicationContext


import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHelper @Inject constructor(
        // Should be Application Context
        @ApplicationContext private val context: Context) {

    // will check for network connectivity
    fun isNetworkConnected(): Boolean {
        return false
    }
}
