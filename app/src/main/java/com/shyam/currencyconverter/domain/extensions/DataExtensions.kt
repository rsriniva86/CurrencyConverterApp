package com.shyam.currencyconverter.domain.extensions

import com.shyam.currencyconverter.data.source.local.database.entities.CurrencyList
import com.shyam.currencyconverter.domain.usecases.ConvertCurrencyUseCase
import com.shyam.currencyconverter.presentation.adapter.CurrencyConversionItem
import java.math.BigDecimal

fun ConvertCurrencyUseCase.ConvertCurrencyResponse.convertToCurrencyConversionItemList(multiplier: BigDecimal): List<CurrencyConversionItem> {

    val outputList = mutableListOf<CurrencyConversionItem>()
    val myMap = this.output
    val myMapIterator = myMap.iterator()
    myMapIterator.forEach {
        outputList.add(CurrencyConversionItem(it.key, it.value, multiplier))
    }
    return outputList

}