package lamarao.jose.weatherapp.ui.cities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import lamarao.jose.weatherapp.R
import lamarao.jose.weatherapp.database.Weather_Cities
import lamarao.jose.weatherapp.database.cities_weather_class
import lamarao.jose.weatherapp.databinding.CityItemBinding


class RvCitiesAdapter: RecyclerView.Adapter<CitiesViewHolder>() {

    var data: List<Weather_Cities> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val withDataBinding : CityItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CitiesViewHolder.LAYOUT,
            parent,
            false)
        return CitiesViewHolder(withDataBinding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.viewDataBinding.also {
           it.weatherCities = data[position]
        }
    }
}

class CitiesViewHolder(val viewDataBinding: CityItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.city_item
    }
}



