package app.test.countries

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import app.test.countries.api.models.Countries
import kotlinx.android.synthetic.main.counry_row.view.*

class CountriesAdapter(
    private val context: Context,
    private val countrySelectedListener: ICountrySelectedListener
) : RecyclerView.Adapter<ViewHolder>() {

    var countries: List<Countries> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.counry_row,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countries[position]

        holder.emojiText.text = country.emoji
        holder.countryName.text = country.name
        holder.countryCard.setOnClickListener {
            countrySelectedListener.onCountrySelected(country)
        }
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val countryName: TextView = view.nameText
    val emojiText: TextView = view.emojiText
    val countryCard: CardView = view.countryCard
}