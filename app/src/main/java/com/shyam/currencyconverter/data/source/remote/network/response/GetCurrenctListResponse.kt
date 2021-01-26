package com.shyam.currencyconverter.data.source.remote.network.response

import com.google.gson.annotations.SerializedName

class GetCurrenctListResponse {

    @SerializedName("currencies")
    var currencies: Map<String,String>?=null

}