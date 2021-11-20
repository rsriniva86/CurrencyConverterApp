package com.shyam.currencyconverter.presentation.view

import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.shyam.currencyconverter.databinding.MainFragmentBinding
import com.shyam.currencyconverter.presentation.adapter.CurrencyConversionAdapter
import com.shyam.currencyconverter.presentation.adapter.CurrencyListAdapter
import com.shyam.currencyconverter.presentation.extensions.afterTextChanged
import com.shyam.currencyconverter.presentation.utils.DecimalDigitsInputFilter
import com.shyam.currencyconverter.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    val viewModel: MainViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding

    private val currencyConversionAdapter: CurrencyConversionAdapter = CurrencyConversionAdapter()
    private lateinit var currencyListAdapter: CurrencyListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupView(view)
    }

     private fun setupView(view: View) {

        with(binding.currencyConversionRecyclerView) {
            layoutManager = GridLayoutManager(context, GRID_MAX_COLS)
            adapter = currencyConversionAdapter
        }

        context?.let {
            currencyListAdapter =
                CurrencyListAdapter(
                    binding.currencyListSpinner,
                    it,
                    android.R.layout.simple_spinner_dropdown_item,
                    mutableListOf<String>()
                )
            with(binding.currencyListSpinner) {
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


        with(binding.amount) {
            filters =
                arrayOf<InputFilter>(DecimalDigitsInputFilter(7, 2))
        }
        binding.amount.afterTextChanged { viewModel.updateMultiplier(it) }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel.onCreate()

    }

     fun setupObservers() {
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