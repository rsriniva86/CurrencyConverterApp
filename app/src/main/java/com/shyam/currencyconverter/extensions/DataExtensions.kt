package com.shyam.currencyconverter.extensions

import com.shyam.currencyconverter.domain.usecases.ConvertCurrencyUseCase
import com.shyam.currencyconverter.domain.usecases.GetCurrencyListUseCase
import com.shyam.currencyconverter.presentation.adapter.CurrencyConversionItem
import java.math.BigDecimal

fun ConvertCurrencyUseCase.ConvertCurrencyResponse.convertToCurrencyConversionItemList(multiplier:BigDecimal): List<CurrencyConversionItem>{

    val outputList= mutableListOf<CurrencyConversionItem>()
    val myMap=this.output
    val myMapIterator=myMap.iterator()
    myMapIterator.forEach {
        outputList.add(CurrencyConversionItem(it.key,it.value,multiplier))
    }
    return outputList

}

fun GetCurrencyListUseCase.GetCurrencyListResponse.convertToCurrencyListString():List<String>{
    val outputList= mutableListOf<String>()
    val myMap=this.output?.currencies
    val myMapIterator=myMap?.iterator()
    myMapIterator?.forEach {
        outputList.add("${it.key}")
    }
    return outputList
}