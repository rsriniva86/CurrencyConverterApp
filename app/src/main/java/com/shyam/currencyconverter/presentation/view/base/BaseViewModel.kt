package com.shyam.currencyconverter.presentation.view.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shyam.currencyconverter.utils.NetworkHelper

abstract class BaseViewModel() : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }

    val messageStringId: MutableLiveData<Int> = MutableLiveData()
    val messageString: MutableLiveData<String> = MutableLiveData()


   

    abstract fun onCreate()
}