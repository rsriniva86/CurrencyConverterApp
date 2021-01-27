package com.shyam.currencyconverter.data.source.remote.network.response

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

class GetCurrencyRatesResponse {
    @SerializedName("quotes")
    var currencies: Map<String, BigDecimal>? = null
}