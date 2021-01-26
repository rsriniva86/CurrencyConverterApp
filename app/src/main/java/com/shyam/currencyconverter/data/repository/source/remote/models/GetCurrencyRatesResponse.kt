package com.shyam.currencyconverter.data.repository.source.remote.models

import com.google.gson.annotations.SerializedName

class GetCurrencyRatesResponse {
    @SerializedName("quotes")
    var currencies: Map<String,Double>?=null
}