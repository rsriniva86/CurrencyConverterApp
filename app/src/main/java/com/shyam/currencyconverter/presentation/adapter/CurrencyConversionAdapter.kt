package com.shyam.currencyconverter.presentation.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shyam.currencyconverter.R
import com.shyam.currencyconverter.extensions.inflate
import kotlinx.android.synthetic.main.item_currency_conversion_card.view.*

class CurrencyConversionAdapter :
    RecyclerView.Adapter<CurrencyConversionAdapter.CurrencyViewHolder>() {

    val conversionList = mutableListOf<CurrencyConversionItem>()

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currencyRateTextView: TextView = itemView.currencyRateTextView
        fun bind(item: CurrencyConversionItem) {
            val mutipliedValue:Double = item.multiplier * item.rate
            currencyRateTextView.text = "${item.currency} : ${item.rate} \n ${mutipliedValue}"
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflatedView = parent.inflate(R.layout.item_currency_conversion_card, false)
        return CurrencyViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = conversionList.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(conversionList[position])
    }

    fun updateList(updatedConversionList: List<CurrencyConversionItem>) {
        conversionList.clear()
        conversionList.addAll(updatedConversionList)
        notifyDataSetChanged()
    }
    fun updateListMultiplier(multiplier: Double) {
        conversionList.forEach{
            it.multiplier=multiplier
        }
        notifyDataSetChanged()
    }



}

data class CurrencyConversionItem(val currency: String, val rate: Double,var multiplier:Double)