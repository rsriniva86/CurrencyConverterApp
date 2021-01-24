package com.shyam.currencyconverter.di.component


import com.shyam.currencyconverter.di.module.ActivityModule
import com.shyam.currencyconverter.presentation.view.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)
}
