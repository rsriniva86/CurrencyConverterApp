package com.shyam.currencyconverter.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shyam.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.shyam.currencyconverter.domain.usecase.GetCurrencyListUseCase
import com.shyam.currencyconverter.domain.usecase.base.UseCase.UseCaseCallback
import com.shyam.currencyconverter.extensions.convertToCurrencyConversionItemList
import com.shyam.currencyconverter.extensions.convertToCurrencyListString
import com.shyam.currencyconverter.ui.adapter.CurrencyConversionItem
import com.shyam.currencyconverter.ui.view.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel() : BaseViewModel() {

    private val _currencyConversionData = MutableLiveData<List<CurrencyConversionItem>>()
    val currencyConversionData: LiveData<List<CurrencyConversionItem>> = _currencyConversionData

    private val _currencyMapLiveData = MutableLiveData<Map<String, String>>()

    private val _currencyListData = MutableLiveData<List<String>>()
    val currencyListData: LiveData<List<String>> = _currencyListData

    private val _multiplierLiveData = MutableLiveData<Double>()
    val multiplierLiveData: LiveData<Double> = _multiplierLiveData

    var amountDouble = 1.0
    override fun onCreate() {
        fetchData()
    }

    fun updateMultiplier(amount: String) {
        amountDouble = amount.toDoubleOrNull() ?: 1.0
        _multiplierLiveData.postValue(amountDouble)
    }

    fun updateBaseCurrency(baseCurrency: String) {
        getConversionList(
            baseCurrency = baseCurrency.substring(0, 3),
            currencyMap = _currencyMapLiveData.value ?: mapOf()
        )
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
                        Log.d(TAG, "onSuccess")
                        val currencyListItems = response.convertToCurrencyListString()
                        _currencyListData.postValue(currencyListItems)
                        _currencyMapLiveData.postValue(response.output?.currencies)
                    }

                    override fun onError(t: Throwable) {
                        Log.d(TAG, "onError")
                        Log.d(TAG, t.message)
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
                        val listOfConversionItems = response.convertToCurrencyConversionItemList(
                            _multiplierLiveData.value ?: 1.0
                        )
                        _currencyConversionData.postValue(listOfConversionItems)
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