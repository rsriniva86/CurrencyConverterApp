package com.shyam.currencyconverter.domain.usecase

import com.shyam.currencyconverter.data.repository.CurrencyRatesRepository
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepositoryImpl
import com.shyam.currencyconverter.data.repository.source.local.database.entities.CurrencyList
import com.shyam.currencyconverter.domain.usecase.base.UseCase

class GetCurrencyListUseCase :
    UseCase<GetCurrencyListUseCase.GetCurrencyListRequest, GetCurrencyListUseCase.GetCurrencyListResponse>() {


    class GetCurrencyListRequest() : UseCase.RequestValues
    data class GetCurrencyListResponse(val output: CurrencyList?) : UseCase.ResponseValue

    override suspend fun executeUseCase(requestValues: GetCurrencyListRequest?) {
        val repository: CurrencyRatesRepository = CurrencyRatesRepositoryImpl()
        val currencyList = repository.getCurrencyList(true)
        val myMap = currencyList.data?.currencies
        myMap?.forEach {
            System.out.println("GetCurrencyListUseCase::Key is ${it.key} value is ${it.value}")
        }
        useCaseCallback?.onSuccess(GetCurrencyListResponse(currencyList.data))
    }
}