package com.shyam.currencyconverter.presentation.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.shyam.currencyconverter.R
import com.shyam.currencyconverter.presentation.adapter.CurrencyConversionAdapter
import com.shyam.currencyconverter.presentation.view.base.BaseFragment
import com.shyam.currencyconverter.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : BaseFragment<MainViewModel>() {

    override fun provideLayoutId(): Int = R.layout.main_fragment

    val currencyConversionAdapter:CurrencyConversionAdapter= CurrencyConversionAdapter()

    override fun setupView(view: View) {
        currencyConversionRecyclerView.layoutManager=GridLayoutManager(context,3)
        currencyConversionRecyclerView.adapter=currencyConversionAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.testData.observe(viewLifecycleOwner, Observer {
            currencyConversionAdapter.updateList(it)
        })
    }
    companion object {

        val TAG = "MainFragment"

        fun newInstance(): MainFragment {
            val args = Bundle()
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }


}