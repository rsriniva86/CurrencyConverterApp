package com.shyam.currencyconverter.data.repository.remote.response

import com.google.gson.annotations.SerializedName

class GetCurrencyRatesResponse {
    @SerializedName("quotes")
    var currencies: Map<String,Double>?=null
}