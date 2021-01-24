package com.shyam.currencyconverter.di.component

import com.shyam.currencyconverter.di.module.FragmentModule
import com.shyam.currencyconverter.presentation.view.MainFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(fragment: MainFragment)
}
