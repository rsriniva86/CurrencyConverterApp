package com.shyam.currencyconverter.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shyam.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.shyam.currencyconverter.domain.usecase.GetCurrencyListUseCase
import com.shyam.currencyconverter.domain.usecase.base.UseCase.UseCaseCallback
import com.shyam.currencyconverter.extensions.convertToCurrencyConversionItemList
import com.shyam.currencyconverter.presentation.adapter.CurrencyConversionItem
import com.shyam.currencyconverter.presentation.view.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel() : BaseViewModel() {

    private val _testData = MutableLiveData<List<CurrencyConversionItem>>()
    val testData: LiveData<List<CurrencyConversionItem>> = _testData

    override fun onCreate() {
        fetchData()
    }

    fun fetchData() {
        getCurrencyList()
    }

    /**
     * Heavy operation that cannot be done in the Main Thread
     */
    fun getCurrencyList() {
        ioScope.launch {
            val myUseCase = GetCurrencyListUseCase()
            myUseCase.useCaseCallback =
                object : UseCaseCallback<GetCurrencyListUseCase.GetCurrencyListResponse> {
                    override fun onSuccess(response: GetCurrencyListUseCase.GetCurrencyListResponse) {
                        System.out.println("onSuccess")

                        val myMap = response.output?.currencies
                        myMap?.forEach {
                            System.out.println(it.key)
                            System.out.println(it.value)
                        }
                        myMap?.let {
                            getConversionList("INR", it)
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
    fun getConversionList(baseCurrency: String, currencyMap: Map<String, String>) {
        ioScope.launch {
            val myUseCase = ConvertCurrencyUseCase()
            myUseCase.useCaseCallback =
                object : UseCaseCallback<ConvertCurrencyUseCase.ConvertCurrencyResponse> {
                    override fun onSuccess(response: ConvertCurrencyUseCase.ConvertCurrencyResponse) {
                        Log.d(TAG, "onSuccess")
                        val listOfConversionItems = response.convertToCurrencyConversionItemList()
                        _testData.postValue(listOfConversionItems)
                    }

                    override fun onError(t: Throwable) {
                        Log.d(TAG, "onError")
                        Log.d(TAG, t.message)
                    }

                }
            myUseCase.executeUseCase(
                ConvertCurrencyUseCase.ConvertCurrencyRequest(
                    baseCurrency = baseCurrency,
                    currencyMap = currencyMap
                )
            )

        }
    }

    companion object {
        val TAG = MainViewModel::class.simpleName
    }
}