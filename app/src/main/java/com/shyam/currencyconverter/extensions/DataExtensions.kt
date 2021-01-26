package com.shyam.currencyconverter.extensions

import com.shyam.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.shyam.currencyconverter.presentation.adapter.CurrencyConversionItem

fun ConvertCurrencyUseCase.ConvertCurrencyResponse.convertToCurrencyConversionItemList(): List<CurrencyConversionItem>{

    val outputList= mutableListOf<CurrencyConversionItem>()
    val myMap=this.output
    val myMapIterator=myMap.iterator()
    myMapIterator.forEach {
        outputList.add(CurrencyConversionItem(it.key,it.value))
    }
    return outputList

}