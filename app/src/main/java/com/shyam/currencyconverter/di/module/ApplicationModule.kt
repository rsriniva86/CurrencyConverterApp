package com.shyam.currencyconverter.di.module

import android.content.Context
import com.shyam.currencyconverter.CurrencyConverterApplication
import com.shyam.currencyconverter.data.source.local.database.CurrencyDatabase
import com.shyam.currencyconverter.data.source.remote.network.CurrencyLayerApiInterface
import com.shyam.currencyconverter.data.source.remote.network.RetrofitClient
import com.shyam.currencyconverter.util.NetworkConnectionChecker
import com.shyam.currencyconverter.util.NetworkConnectionCheckerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): CurrencyConverterApplication {
        return app as CurrencyConverterApplication
    }

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }


    @Provides
    @Singleton
    fun providesDatabase(application: CurrencyConverterApplication): CurrencyDatabase = CurrencyDatabase.invoke(application)

    @Provides
    @Singleton
    fun providesRetrofitClient():CurrencyLayerApiInterface = RetrofitClient.CURRENCY_LAYER_API_INTERFACE


    @Provides
    @Singleton
    fun providesNetworkChecker(application:CurrencyConverterApplication): NetworkConnectionChecker =
        NetworkConnectionCheckerImpl(application)



}