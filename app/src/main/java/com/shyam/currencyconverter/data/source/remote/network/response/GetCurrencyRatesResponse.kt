package com.shyam.currencyconverter.data.source.remote.network.response

import com.google.gson.annotations.SerializedName

class GetCurrencyRatesResponse {
    @SerializedName("quotes")
    var currencies: Map<String,Double>?=null
}