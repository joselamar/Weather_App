package lamarao.jose.weatherapp.ui.current_Location

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import lamarao.jose.weatherapp.R
import lamarao.jose.weatherapp.database.Hourly
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

@SuppressLint("SetTextI18n")
class rv_hourly_adapter (private val dataSet: List<Hourly>, val unit: String): RecyclerView.Adapter<rv_hourly_adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hour_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (position==0) {
            viewHolder.hour.text = "Now"
            viewHolder.hour.setTypeface(null, Typeface.BOLD)
        } else {
           viewHolder.hour.text = handleHour(dataSet[position].dt)
        }
        if(dataSet[position].pop>=0.3) {
            viewHolder.prob_rain.text = round(dataSet[position].pop * 100).toInt().toString() + "%"
            viewHolder.prob_rain.setTextColor(Color.rgb(51, 170, 255))
            val params = viewHolder.icon_hourly.layoutParams as ConstraintLayout.LayoutParams
            params.topToBottom = viewHolder.prob_rain.id
            params.bottomToTop = viewHolder.hour_weather.id
            viewHolder.icon_hourly.requestLayout()
            val params1 = viewHolder.prob_rain.layoutParams as ConstraintLayout.LayoutParams
            params1.topToBottom = viewHolder.hour.id
            params1.bottomToTop = viewHolder.icon_hourly.id
            viewHolder.prob_rain.requestLayout()
        } else {
            val params = viewHolder.icon_hourly.layoutParams as ConstraintLayout.LayoutParams
            params.topToBottom = viewHolder.hour.id
            params.bottomToTop = viewHolder.hour_weather.id
            viewHolder.icon_hourly.requestLayout()
            viewHolder.prob_rain.text = " "
        }
        viewHolder.icon_hourly.setImageResource(viewHolder.itemView.context.resources.getIdentifier("ic_"+dataSet[position].weather[0].icon,"drawable",viewHolder.itemView.context.packageName))
        viewHolder.hour_weather.text = round(dataSet[position].temp).toInt().toString()+"º"

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hour: TextView
        val prob_rain: TextView
        val icon_hourly: ImageView
        val hour_weather: TextView

        init {
            hour = view.findViewById(R.id.hour)
            prob_rain = view.findViewById(R.id.prob_rain)
            icon_hourly = view.findViewById(R.id.icon_hourly)
            hour_weather = view.findViewById(R.id.weather_hourly)
        }
    }

    private fun handleHour(dt: Long): String? {
        val calendar = Calendar.getInstance();
        calendar.timeInMillis = dt * 1000L
        val sdf = SimpleDateFormat("HH", Locale.getDefault())
        return sdf.format(Date(calendar.timeInMillis))
    }

}