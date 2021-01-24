package com.shyam.currencyconverter.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shyam.currencyconverter.data.repository.source.local.database.DatabaseService
import com.shyam.currencyconverter.data.repository.source.remote.network.NetworkService
import com.shyam.currencyconverter.presentation.view.base.BaseViewModel
import com.shyam.currencyconverter.utils.NetworkHelper

class MainViewModel(
    networkHelper: NetworkHelper,
    private val databaseService: DatabaseService,
    private val networkService: NetworkService
) : BaseViewModel(networkHelper = networkHelper) {

    val testData = MutableLiveData<String>()

    override fun onCreate() {
        testData.postValue("Hello from MainViewModel")
    }
}