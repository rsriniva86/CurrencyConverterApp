package com.shyam.currencyconverter.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.shyam.currencyconverter.data.repository.source.local.database.CurrencyDatabase
import com.shyam.currencyconverter.data.repository.source.remote.network.RetrofitClient
import com.shyam.currencyconverter.di.component.ActivityContext
import com.shyam.currencyconverter.presentation.view.base.BaseActivity
import com.shyam.currencyconverter.presentation.viewmodel.MainViewModel
import com.shyam.currencyconverter.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides


@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = activity

    @Provides
    fun provideMainViewModel(
            databaseService: CurrencyDatabase,
            networkService: RetrofitClient
    ): MainViewModel = ViewModelProviders.of(
            activity, ViewModelProviderFactory(MainViewModel::class) {
        MainViewModel()
    }).get(MainViewModel::class.java)
}
