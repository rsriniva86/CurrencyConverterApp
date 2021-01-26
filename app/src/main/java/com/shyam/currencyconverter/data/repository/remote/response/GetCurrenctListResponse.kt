package com.shyam.currencyconverter.data.repository.remote.response

import com.google.gson.annotations.SerializedName

class GetCurrenctListResponse {

    @SerializedName("currencies")
    var currencies: Map<String,String>?=null

}