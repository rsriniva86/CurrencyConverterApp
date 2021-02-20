package com.shyam.currencyconverter.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepository
import com.shyam.currencyconverter.domain.usecases.ConvertCurrencyUseCase
import com.shyam.currencyconverter.domain.usecases.CurrencyListUseCase
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
        currencyListUseCase: CurrencyListUseCase,
        convertCurrencyUseCase: ConvertCurrencyUseCase
    ): MainViewModel = ViewModelProviders.of(
        fragment,
        ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(
                networkConnectionChecker=networkHelper,
                currencyListUseCase = currencyListUseCase,
                convertCurrencyUseCase = convertCurrencyUseCase
            )
        }
    ).get(MainViewModel::class.java)
}