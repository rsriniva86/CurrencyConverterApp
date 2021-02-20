package com.shyam.currencyconverter.di.module

import com.shyam.currencyconverter.data.repository.CurrencyRatesRepository
import com.shyam.currencyconverter.data.repository.CurrencyRatesRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providesCurrencyRatesRepository(impl:CurrencyRatesRepositoryImpl): CurrencyRatesRepository

}