package com.shyam.currencyconverter.presentation.adapter

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shyam.currencyconverter.databinding.ItemCurrencyConversionCardBinding
import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyConversionAdapter :
    RecyclerView.Adapter<CurrencyConversionAdapter.CurrencyViewHolder>() {

    val conversionList = mutableListOf<CurrencyConversionItem>()
    private lateinit var binding: ItemCurrencyConversionCardBinding

    class CurrencyViewHolder(itemView: ItemCurrencyConversionCardBinding) : RecyclerView.ViewHolder(itemView.root) {
        val currencyRateTextView: TextView = itemView.currencyRateTextView
        fun bind(item: CurrencyConversionItem) {
            val decimalMutipliedValue =
                (item.multiplier * item.rate).setScale(2, RoundingMode.HALF_EVEN)

            var htmlString =
                "Currency: <b><font color = blue>${item.currency.substring(3)}</font> </b> <br>" +
                        "Value:<b><font color = blue> ${decimalMutipliedValue}</font></b> <br>"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                currencyRateTextView.text = Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT)
            } else {
                @Suppress("DEPRECATION")
                currencyRateTextView.text = Html.fromHtml(htmlString)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        binding= ItemCurrencyConversionCardBinding.inflate(LayoutInflater.from(parent.context!!), parent, false)
        return CurrencyViewHolder(binding)
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

    fun updateListMultiplier(multiplier: BigDecimal) {
        conversionList.forEach {
            it.multiplier = multiplier
        }
        notifyDataSetChanged()
    }

}

data class CurrencyConversionItem(
    val currency: String,
    val rate: BigDecimal,
    var multiplier: BigDecimal
)