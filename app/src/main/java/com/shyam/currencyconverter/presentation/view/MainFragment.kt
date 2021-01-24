package com.shyam.currencyconverter.presentation.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.shyam.currencyconverter.R
import com.shyam.currencyconverter.di.component.FragmentComponent
import com.shyam.currencyconverter.presentation.view.base.BaseFragment
import com.shyam.currencyconverter.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : BaseFragment<MainViewModel>() {

    companion object {

        val TAG = "MainFragment"

        fun newInstance(): MainFragment {
            val args = Bundle()
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun provideLayoutId(): Int = R.layout.main_fragment

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

//    override fun injectDependencies(fragmentComponent: FragmentComponent){
//
//    }
    override fun setupView(view: View) {

    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.testData.observe(this, Observer {
            println(it)
        })
    }

}