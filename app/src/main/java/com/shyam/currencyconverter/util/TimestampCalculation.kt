package com.shyam.currencyconverter.util

import java.util.concurrent.TimeUnit

object TimestampCalculation {

    fun generateTimestamp(): Long= TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
    fun isTimestampStale(timestamp: Long):Boolean{
        val currentTimestamp= generateTimestamp()
        return ((currentTimestamp - timestamp) > 30 * 60)
    }
}