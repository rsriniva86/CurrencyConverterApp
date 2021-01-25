package com.shyam.currencyconverter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepository
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepositoryImpl
import com.shyam.currencyconverter.data.repository.source.local.database.DatabaseService
import com.shyam.currencyconverter.data.repository.source.remote.network.NetworkService
import com.shyam.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.shyam.currencyconverter.domain.usecase.GetCurrencyListUseCase
import com.shyam.currencyconverter.domain.usecase.base.UseCase
import com.shyam.currencyconverter.domain.usecase.base.UseCase.UseCaseCallback
import com.shyam.currencyconverter.presentation.view.base.BaseViewModel
import com.shyam.currencyconverter.utils.NetworkHelper
import kotlinx.coroutines.launch

class MainViewModel() : BaseViewModel() {

    private val _testData = MutableLiveData<String>()
    val testData: LiveData<String> = _testData

    override fun onCreate() {
        _testData.postValue("Hello from MainViewModel")
        fetchData()
    }

    fun fetchData(){
        launchDataLoad()
    }


    /**
     * Heavy operation that cannot be done in the Main Thread
     */
    fun launchDataLoad() {
        uiScope.launch {
            val myUseCase : GetCurrencyListUseCase = GetCurrencyListUseCase()
            myUseCase.useCaseCallback = object : UseCaseCallback<GetCurrencyListUseCase.GetCurrencyListResponse>{
                override fun onSuccess(response: GetCurrencyListUseCase.GetCurrencyListResponse) {
                    System.out.println("onSuccess")

                    val myMap=response.output?.currencies
                    myMap?.forEach {
                        System.out.println(it.key)
                        System.out.println(it.value)
                    }


                }

                override fun onError(t: Throwable) {
                    System.out.println("onError")
                    System.out.println(t.message)

                }

            }
            myUseCase.executeUseCase(GetCurrencyListUseCase.GetCurrencyListRequest())

        }
    }
}