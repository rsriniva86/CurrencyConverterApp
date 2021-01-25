package com.shyam.currencyconverter.data.repository.source.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetCurrenctListResponse {

    @SerializedName("currencies")
    var currencies: Map<String,String>?=null

}