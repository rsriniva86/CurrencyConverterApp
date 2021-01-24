package com.shyam.currencyconverter.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.shyam.currencyconverter.data.repository.source.local.database.DatabaseService
import com.shyam.currencyconverter.data.repository.source.remote.network.NetworkService
import com.shyam.currencyconverter.di.component.ActivityContext
import com.shyam.currencyconverter.di.component.FragmentContext
import com.shyam.currencyconverter.presentation.view.base.BaseFragment
import com.shyam.currencyconverter.presentation.viewmodel.MainViewModel
import com.shyam.currencyconverter.utils.NetworkHelper
import com.shyam.currencyconverter.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides


@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @FragmentContext
    @Provides
    fun provideContext(): Context = fragment.context!!


    @Provides
    fun provideMainViewModel(
        networkHelper: NetworkHelper,
        databaseService: DatabaseService,
        networkService: NetworkService
    ): MainViewModel = ViewModelProviders.of(
            fragment, ViewModelProviderFactory(MainViewModel::class) {
        MainViewModel(networkHelper, databaseService, networkService)
    }).get(MainViewModel::class.java)
}
