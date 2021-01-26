package com.shyam.currencyconverter.presentation.view.base
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer


/**
 * Reference for generics: https://kotlinlang.org/docs/reference/generics.html
 * Basically BaseFragment will take any class that extends BaseViewModel
 */
abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    open lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel.onCreate()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(provideLayoutId(), container, false)

    protected open fun setupObservers() {
        viewModel.messageString.observe(this, Observer {
            showMessage(it)
        })

        viewModel.messageStringId.observe(this, Observer {
            showMessage(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupView(view)
    }


    fun showMessage(message: String) = context?.let { Toast.makeText(it, message, Toast.LENGTH_SHORT).show() }

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    fun goBack() {
        if (activity is BaseActivity<*>) (activity as BaseActivity<*>).goBack()
    }

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun setupView(view: View)
}