package com.shyam.currencyconverter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shyam.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.shyam.currencyconverter.domain.usecase.GetCurrencyListUseCase
import com.shyam.currencyconverter.domain.usecase.base.UseCase.UseCaseCallback
import com.shyam.currencyconverter.presentation.view.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel() : BaseViewModel() {

    private val _testData = MutableLiveData<String>()
    val testData: LiveData<String> = _testData

    override fun onCreate() {
        _testData.postValue("Hello from MainViewModel")
        fetchData()
    }

    fun fetchData(){
        getCurrencyList()
        getConversionList()
    }


    /**
     * Heavy operation that cannot be done in the Main Thread
     */
    fun getCurrencyList() {
        ioScope.launch {
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


    /**
     * Heavy operation that cannot be done in the Main Thread
     */
    fun getConversionList() {
        ioScope.launch {
            val myUseCase : ConvertCurrencyUseCase = ConvertCurrencyUseCase()
            myUseCase.useCaseCallback = object : UseCaseCallback<ConvertCurrencyUseCase.ConvertCurrencyResponse>{
                override fun onSuccess(response: ConvertCurrencyUseCase.ConvertCurrencyResponse) {
                    System.out.println("onSuccess")

                    val myMap=response.output
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
            myUseCase.executeUseCase(ConvertCurrencyUseCase.ConvertCurrencyRequest("USD"))

        }
    }
}