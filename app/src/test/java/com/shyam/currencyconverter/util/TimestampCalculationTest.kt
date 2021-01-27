package com.shyam.currencyconverter.util

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class TimestampCalculationTest {

    @Test
    fun testIsTimestampNotStaleForCurrentTime() {
        assertFalse(
            "current time should not be treated as stale",
            TimestampCalculation.isTimestampStale(TimestampCalculation.generateTimestamp())
        )
    }

    @Test
    fun testIsTimestampStaleForOlderThanThirtyMins() {
        assertTrue(
            "older than 30 mins of current should be treated as stale",
            TimestampCalculation.isTimestampStale(TimestampCalculation.generateTimestamp() - (31 * 60))
        )
    }

    @Test
    fun testIsTimestampStaleForFutureTime() {
        assertFalse(
            "Future time should be treated as not stale",
            TimestampCalculation.isTimestampStale(TimestampCalculation.generateTimestamp() + (1 * 60))
        )
    }
}