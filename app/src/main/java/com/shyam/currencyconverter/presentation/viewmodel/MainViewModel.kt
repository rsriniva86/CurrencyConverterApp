package com.shyam.currencyconverter.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shyam.currencyconverter.core.Event
import com.shyam.currencyconverter.domain.UseCase.UseCaseCallback
import com.shyam.currencyconverter.domain.extensions.convertToCurrencyConversionItemList
import com.shyam.currencyconverter.domain.extensions.convertToCurrencyListString
import com.shyam.currencyconverter.domain.usecases.ConvertCurrencyUseCase
import com.shyam.currencyconverter.domain.usecases.GetCurrencyListUseCase
import com.shyam.currencyconverter.presentation.adapter.CurrencyConversionItem
import com.shyam.currencyconverter.presentation.view.base.BaseViewModel
import com.shyam.currencyconverter.util.NetworkConnectionChecker
import kotlinx.coroutines.launch
import java.math.BigDecimal

class MainViewModel() : BaseViewModel() {


    lateinit var networkConnectionChecker: NetworkConnectionChecker

    /**
     * live data for currencyMap, currency conversion, multiplier amount,network connectivity
     */
    private val _currencyConversionLiveData = MutableLiveData<List<CurrencyConversionItem>>()
    val currencyConversionData: LiveData<List<CurrencyConversionItem>> = _currencyConversionLiveData
    private val _currencyMapLiveData = MutableLiveData<Map<String, String>>()
    private val _currencyListData = MutableLiveData<List<String>>()
    val currencyListData: LiveData<List<String>> = _currencyListData
    private val _multiplierLiveData = MutableLiveData<BigDecimal>()
    val multiplierLiveData: LiveData<BigDecimal> = _multiplierLiveData
    private val _networkConnectivityLiveData = MutableLiveData<Event<Boolean>>()
    val networkConnectivityLiveData: LiveData<Event<Boolean>> = _networkConnectivityLiveData

    private var amountBigDecimal: BigDecimal = BigDecimal(1.0)
    private var isNetworkConnected: Boolean = false

    override fun onCreate() {
        fetchData()
    }

    fun updateMultiplier(amount: String) {
        amountBigDecimal = amount.toBigDecimalOrNull() ?: BigDecimal(1.0)
        _multiplierLiveData.postValue(amountBigDecimal)
    }

    fun updateBaseCurrency(baseCurrency: String) {
        getConversionList(
            baseCurrency = baseCurrency.substring(0, 3),
            currencyMap = _currencyMapLiveData.value ?: mapOf()
        )
    }

    private fun fetchData() {
        isNetworkConnected = networkConnectionChecker.isConnected()
        _networkConnectivityLiveData.value = Event(isNetworkConnected)
        getCurrencyList()
    }

    /**
     * Heavy operation that cannot be done in the Main Thread
     */
    private fun getCurrencyList() {
        ioScope.launch {
            val myUseCase = GetCurrencyListUseCase()
            myUseCase.useCaseCallback =
                object : UseCaseCallback<GetCurrencyListUseCase.GetCurrencyListResponse> {
                    override fun onSuccess(response: GetCurrencyListUseCase.GetCurrencyListResponse) {
                        Log.d(TAG, "onSuccess")
                        val currencyListItems = response.convertToCurrencyListString()
                        _currencyListData.postValue(currencyListItems)
                        this@MainViewModel._currencyMapLiveData.postValue(response.output?.currencies)
                    }

                    override fun onError(t: Throwable) {
                        Log.d(TAG, "onError")
                        Log.d(TAG, t.message as String)
                    }
                }
            myUseCase.executeUseCase(
                GetCurrencyListUseCase.GetCurrencyListRequest(
                    isNetworkConnected
                )
            )
        }
    }


    /**
     * Heavy operation that cannot be done in the Main Thread
     */
    private fun getConversionList(baseCurrency: String, currencyMap: Map<String, String>) {
        ioScope.launch {
            val myUseCase = ConvertCurrencyUseCase()
            myUseCase.useCaseCallback =
                object : UseCaseCallback<ConvertCurrencyUseCase.ConvertCurrencyResponse> {
                    override fun onSuccess(response: ConvertCurrencyUseCase.ConvertCurrencyResponse) {
                        Log.d(TAG, "onSuccess")
                        val listOfConversionItems = response.convertToCurrencyConversionItemList(
                            _multiplierLiveData.value ?: BigDecimal(1.0)
                        )
                        _currencyConversionLiveData.postValue(listOfConversionItems)
                    }

                    override fun onError(t: Throwable) {
                        Log.d(TAG, "onError")
                        Log.d(TAG, t.message as String)
                    }

                }
            myUseCase.executeUseCase(
                ConvertCurrencyUseCase.ConvertCurrencyRequest(
                    baseCurrency = baseCurrency,
                    currencyMap = currencyMap,
                    isNetworkConnected = isNetworkConnected
                )
            )

        }
    }


    companion object {
        private val TAG = MainViewModel::class.simpleName
    }
}