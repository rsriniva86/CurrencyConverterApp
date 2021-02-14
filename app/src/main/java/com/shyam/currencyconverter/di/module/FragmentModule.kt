package com.shyam.currencyconverter.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepository
import com.shyam.currencyconverter.presentation.view.base.BaseFragment
import com.shyam.currencyconverter.presentation.viewmodel.MainViewModel
import com.shyam.currencyconverter.presentation.viewmodel.ViewModelProviderFactory
import com.shyam.currencyconverter.util.NetworkConnectionChecker
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {


    @Provides
    fun getContext():Context=fragment.context!!

    @Provides
    fun providesMainViewModel(
        networkHelper: NetworkConnectionChecker,
        repository: CurrencyRatesRepository
    ): MainViewModel = ViewModelProviders.of(
        fragment,
        ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(networkHelper, repository)
        }
    ).get(MainViewModel::class.java)
}