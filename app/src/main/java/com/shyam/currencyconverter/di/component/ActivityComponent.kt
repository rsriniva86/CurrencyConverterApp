package com.shyam.currencyconverter.di.component

import com.shyam.currencyconverter.di.module.ActivityModule
import com.shyam.currencyconverter.di.scope.ActivityScope
import com.shyam.currencyconverter.presentation.view.MainActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [
        ActivityModule::class
    ],
    dependencies = [
        ApplicationComponent::class
    ]
)
interface ActivityComponent {
    fun inject(activity: MainActivity)
}