package com.shyam.currencyconverter.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.shyam.currencyconverter.data.repository.source.local.database.CurrencyDatabase
import com.shyam.currencyconverter.data.repository.source.remote.network.RetrofitClient
import com.shyam.currencyconverter.di.component.ActivityContext
import com.shyam.currencyconverter.presentation.view.base.BaseFragment
import com.shyam.currencyconverter.presentation.viewmodel.MainViewModel
import com.shyam.currencyconverter.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides


@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = fragment.context!!

    @Provides
    fun provideMainViewModel(
            databaseService: CurrencyDatabase,
            networkService: RetrofitClient
    ): MainViewModel = ViewModelProviders.of(
            fragment, ViewModelProviderFactory(MainViewModel::class) {
        MainViewModel()
    }).get(MainViewModel::class.java)
}
