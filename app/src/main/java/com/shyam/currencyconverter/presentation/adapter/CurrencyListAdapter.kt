package com.shyam.currencyconverter.presentation.adapter

import android.content.Context
import android.widget.ArrayAdapter

class CurrencyListAdapter(
    context:Context,
    resource:Int,
    myArray: MutableList<String>
) : ArrayAdapter<String>(context,resource,myArray) {

    fun updateArray(updatedArray:List<String>){
        this.clear()
        this.addAll(updatedArray)
        this.notifyDataSetChanged()
    }

}
