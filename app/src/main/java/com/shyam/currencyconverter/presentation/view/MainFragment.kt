package com.shyam.currencyconverter.presentation.view

import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.shyam.currencyconverter.CurrencyConverterApplication
import com.shyam.currencyconverter.R
import com.shyam.currencyconverter.presentation.adapter.CurrencyConversionAdapter
import com.shyam.currencyconverter.presentation.adapter.CurrencyListAdapter
import com.shyam.currencyconverter.presentation.extensions.afterTextChanged
import com.shyam.currencyconverter.presentation.utils.DecimalDigitsInputFilter
import com.shyam.currencyconverter.presentation.view.base.BaseFragment
import com.shyam.currencyconverter.presentation.viewmodel.MainViewModel
import com.shyam.currencyconverter.util.NetworkHelper
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : BaseFragment<MainViewModel>() {

    override fun provideLayoutId(): Int = R.layout.main_fragment

    val currencyConversionAdapter: CurrencyConversionAdapter = CurrencyConversionAdapter()
    lateinit var currencyListAdapter: CurrencyListAdapter

    override fun setupView(view: View) {

        CurrencyConverterApplication.getContext()?.let {
            val isNetworkAvailable = NetworkHelper.isNetworkAvailable(it)
            if (!isNetworkAvailable) {
                Log.d(TAG, "network is not available")
                Toast.makeText(this.context, "Network is not available", Toast.LENGTH_LONG).show()
            }
        }
        with(currencyConversionRecyclerView) {
            layoutManager = GridLayoutManager(context, 3)
            adapter = currencyConversionAdapter
        }

        context?.let {
            currencyListAdapter =
                CurrencyListAdapter(
                    currencyListSpinner,
                    it,
                    android.R.layout.simple_spinner_dropdown_item,
                    mutableListOf<String>()
                )
            with(currencyListSpinner) {
                adapter = currencyListAdapter
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        viewModel.updateBaseCurrency(parent.getItemAtPosition(position) as String)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                    }
                }

            }

        }
        with(amount) {
            filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(7, 2))
        }
        amount.afterTextChanged { viewModel.updateMultiplier(it) }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.currencyListData.observe(viewLifecycleOwner, Observer {
            currencyListAdapter.updateArray(it)
            currencyListAdapter.selectDefaultItem("JPY")
        })
        viewModel.currencyConversionData.observe(viewLifecycleOwner, Observer {
            currencyConversionAdapter.updateList(it)
        })
        viewModel.multiplierLiveData.observe(viewLifecycleOwner, Observer {
            currencyConversionAdapter.updateListMultiplier(it)
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