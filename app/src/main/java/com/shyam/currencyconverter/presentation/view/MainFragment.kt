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
import com.shyam.currencyconverter.R
import com.shyam.currencyconverter.presentation.adapter.CurrencyConversionAdapter
import com.shyam.currencyconverter.presentation.adapter.CurrencyListAdapter
import com.shyam.currencyconverter.presentation.extensions.afterTextChanged
import com.shyam.currencyconverter.presentation.utils.DecimalDigitsInputFilter
import com.shyam.currencyconverter.presentation.view.base.BaseFragment
import com.shyam.currencyconverter.presentation.viewmodel.MainViewModel
import com.shyam.currencyconverter.util.NetworkConnectionCheckerImpl
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : BaseFragment<MainViewModel>() {

    override fun provideLayoutId(): Int = R.layout.main_fragment

    private val currencyConversionAdapter: CurrencyConversionAdapter = CurrencyConversionAdapter()
    private lateinit var currencyListAdapter: CurrencyListAdapter

    override fun setupView(view: View) {

        with(currencyConversionRecyclerView) {
            layoutManager = GridLayoutManager(context, GRID_MAX_COLS)
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
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        viewModel.updateBaseCurrency(parent.getItemAtPosition(position) as String)
                    }
                }

            }

        }


        with(amount) {
            filters =
                arrayOf<InputFilter>(DecimalDigitsInputFilter(7, 2))
        }
        amount.afterTextChanged { viewModel.updateMultiplier(it) }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java).apply {
            networkConnectionChecker = NetworkConnectionCheckerImpl(context = requireContext())
        }
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

        viewModel.networkConnectivityLiveData.observe(viewLifecycleOwner, Observer {
            if (!it.hasBeenHandled) {
                val isNetworkAvailable = it.getContentIfNotHandled() ?: true
                if (!isNetworkAvailable) {
                    Log.d(TAG, "network is not available")
                    Toast.makeText(this.context, "Network is not available", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })

    }

    companion object {
        private const val TAG = "MainFragment"
        fun newInstance(): MainFragment = MainFragment()
        private const val GRID_MAX_COLS = 3
    }


}