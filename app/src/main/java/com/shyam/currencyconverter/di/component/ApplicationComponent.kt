package com.shyam.currencyconverter.di.component

import com.shyam.currencyconverter.CurrencyConverterApplication
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepositoryImpl
import com.shyam.currencyconverter.di.module.ApplicationModule
import com.shyam.currencyconverter.domain.usecases.ConvertCurrencyUseCase
import com.shyam.currencyconverter.domain.usecases.GetCurrencyListUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: CurrencyConverterApplication)
    fun inject(repository: CurrencyRatesRepositoryImpl)
    fun inject(usecase: GetCurrencyListUseCase)
    fun inject(usecase: ConvertCurrencyUseCase)

}