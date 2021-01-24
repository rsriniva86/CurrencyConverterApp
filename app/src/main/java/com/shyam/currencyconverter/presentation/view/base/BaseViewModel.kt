package com.shyam.currencyconverter.presentation.view.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shyam.currencyconverter.utils.NetworkHelper

abstract class BaseViewModel(
        protected val networkHelper: NetworkHelper
) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }

    val messageStringId: MutableLiveData<Int> = MutableLiveData()
    val messageString: MutableLiveData<String> = MutableLiveData()

    protected fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    protected fun handleNetworkError(err: Throwable?) =
            err?.let {
                // handle the error
            }

    abstract fun onCreate()
}