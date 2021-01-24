package com.shyam.currencyconverter.domain.usecase

import com.shyam.currencyconverter.domain.usecase.base.UseCase

class ConvertCurrencyUseCase : UseCase<ConvertCurrencyUseCase.ConvertCurrencyRequest, ConvertCurrencyUseCase.ConvertCurrencyResponse>() {


    data class ConvertCurrencyRequest(val baseCurrency:String): UseCase.RequestValues
    data class ConvertCurrencyResponse(val output:Map<String,Double>): UseCase.ResponseValue

    override fun executeUseCase(requestValues: ConvertCurrencyRequest?) {



    }
}