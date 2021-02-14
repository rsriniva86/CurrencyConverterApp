package com.shyam.currencyconverter.di.module

import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepository
import com.shyam.currencyconverter.presentation.view.base.BaseActivity
import com.shyam.currencyconverter.presentation.viewmodel.MainViewModel
import com.shyam.currencyconverter.presentation.viewmodel.ViewModelProviderFactory
import com.shyam.currencyconverter.util.NetworkConnectionChecker
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val  activity: BaseActivity<*>) {



}