package com.shyam.currencyconverter.di.component

import com.shyam.currencyconverter.CurrencyConverterApplication
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepository
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepositoryImpl
import com.shyam.currencyconverter.di.module.ApplicationModule
import com.shyam.currencyconverter.di.module.DataSourceModule
import com.shyam.currencyconverter.di.module.RepositoryModule
import com.shyam.currencyconverter.domain.usecases.ConvertCurrencyUseCase
import com.shyam.currencyconverter.domain.usecases.CurrencyListUseCase
import com.shyam.currencyconverter.util.NetworkConnectionChecker
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        DataSourceModule::class,
        RepositoryModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: CurrencyConverterApplication)
    fun inject(repository: CurrencyRatesRepositoryImpl)
    fun inject(useCase: CurrencyListUseCase)
    fun inject(useCase: ConvertCurrencyUseCase)

    fun providesNetworkConnectionChecker():NetworkConnectionChecker
    fun providesCurrencyRepository():CurrencyRatesRepository

}