package lamarao.jose.wit.software.challenge.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import lamarao.jose.wit.software.challenge.R
import lamarao.jose.wit.software.challenge.model.Weather_Class
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

@SuppressLint("SetTextI18n")
class rv_cities_adapter(private val dataSet: List<Weather_Class>, val unit: String): RecyclerView.Adapter<rv_cities_adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rv_cities_adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(viewHolder: rv_cities_adapter.ViewHolder, position: Int) {
        viewHolder.city_time.text = handleTime(dataSet[position].dt+dataSet[position].sys.timezone)
        viewHolder.city_weather.text = round(dataSet[position].main.temp).toInt().toString()+"º"
        viewHolder.city_weather_metric.text = handleTempMetric(unit)
        viewHolder.city_location.text = dataSet[position].name
        viewHolder.city_weather_desc.text = dataSet[position].weather[0].main
        viewHolder.city_weather_wind.text = windDirection(dataSet[position].wind.deg)+" "+dataSet[position].wind.speed+handleWindMetric(unit)
        viewHolder.city_weather_pressure.text = dataSet[position].main.pressure.toString()+"hPA"
        viewHolder.city_weather_humidity.text = dataSet[position].main.humidity.toString()+"%"
        viewHolder.city_weather_icon.setImageResource(viewHolder.itemView.context.resources.getIdentifier("ic_"+dataSet[position].weather[0].icon,"drawable",viewHolder.itemView.context.packageName))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val city_time: TextView
        val city_weather: TextView
        val city_weather_metric : TextView
        val city_location: TextView
        val city_weather_icon: ImageView
        val city_weather_desc: TextView
        val city_weather_wind: TextView
        val city_weather_pressure: TextView
        val city_weather_humidity: TextView

        init {
            city_time = view.findViewById(R.id.city_time)
            city_weather = view.findViewById(R.id.city_weather)
            city_weather_metric = view.findViewById(R.id.city_weather_metric)
            city_location = view.findViewById(R.id.city_location)
            city_weather_icon = view.findViewById(R.id.city_weather_icon)
            city_weather_desc = view.findViewById(R.id.city_weather_desc)
            city_weather_wind = view.findViewById(R.id.city_weather_wind)
            city_weather_pressure = view.findViewById(R.id.city_weather_pressure)
            city_weather_humidity = view.findViewById(R.id.city_weather_humidity)
        }
    }

    private fun handleTime(timestamp : Long) : String {
        val calendar = Calendar.getInstance();
        calendar.timeInMillis = timestamp * 1000L
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(calendar.timeInMillis))
    }

    private fun windDirection(degree: Double): String {
        var degree: Double = degree
        val sectors = arrayOf("N","NE","E","SE","S","SW","W","NW")
        degree += 22.5;
        if (degree < 0)
            degree = 360 - Math.abs(degree) % 360;
        else
            degree = degree % 360;
        var which = (degree / 45).toInt();
        return sectors[which]
    }

    private fun handleWindMetric(selectedUnit: String): String {
        return if (selectedUnit == "metric") " m/s" else " Mi/h"
    }

    private fun handleTempMetric(selectedUnit: String): String {
        return if (selectedUnit == "metric") "C" else "F"
    }
}