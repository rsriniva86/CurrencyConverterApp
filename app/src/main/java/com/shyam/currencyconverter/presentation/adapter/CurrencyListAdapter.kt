package com.shyam.currencyconverter.presentation.adapter

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner

class CurrencyListAdapter(
    val spinner: Spinner,
    context:Context,
    resource:Int,
    myArray: MutableList<String>
) : ArrayAdapter<String>(context,resource,myArray) {

    fun selectDefaultItem(myString:String){
        val spinnerPosition: Int = getPosition(myString)
        if(spinnerPosition>=0) {
            spinner.setSelection(spinnerPosition)
        }
    }

    fun updateArray(updatedArray:List<String>){
        this.clear()
        this.addAll(updatedArray)
        this.notifyDataSetChanged()
    }

}
