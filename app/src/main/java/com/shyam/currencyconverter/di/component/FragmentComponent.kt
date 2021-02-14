package com.shyam.currencyconverter.di.component

import com.shyam.currencyconverter.di.module.FragmentModule
import com.shyam.currencyconverter.di.scope.FragmentScope
import com.shyam.currencyconverter.presentation.view.MainFragment
import dagger.Component


@FragmentScope
@Component(
    modules = [
        FragmentModule::class
    ],
    dependencies = [
        ApplicationComponent::class
    ]
)
interface FragmentComponent {
    fun inject(fragment:MainFragment)
}