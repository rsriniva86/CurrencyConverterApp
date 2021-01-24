package com.shyam.currencyconverter.di.component

import android.content.Context
import com.shyam.currencyconverter.MyApplication
import com.shyam.currencyconverter.data.repository.source.local.database.CurrencyDatabase
import com.shyam.currencyconverter.data.repository.source.local.database.DatabaseService
import com.shyam.currencyconverter.data.repository.source.remote.network.NetworkService
import com.shyam.currencyconverter.data.repository.source.remote.network.RetrofitClient
import com.shyam.currencyconverter.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: MyApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getDatabaseService(): DatabaseService

}
