package lamarao.jose.weatherapp.ui.current_Location

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import lamarao.jose.weatherapp.R
import lamarao.jose.weatherapp.database.Daily
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

@SuppressLint("SetTextI18n")
class rv_weekday_adapter(private val dataSet: List<Daily>, val unit: String): RecyclerView.Adapter<rv_weekday_adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.weekday.text = handleWeekDay(dataSet[position].dt)
        viewHolder.weekday_max.text = round(dataSet[position].temp.max).toInt().toString()+"º"
        viewHolder.weekday_min.text = round(dataSet[position].temp.min).toInt().toString()+"º"
        viewHolder.weathericon.setImageResource(viewHolder.itemView.context.resources.getIdentifier("ic_"+dataSet[position].weather[0].icon,"drawable",viewHolder.itemView.context.packageName))

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val weekday: TextView
        val weathericon: ImageView
        val weekday_max: TextView
        val weekday_min: TextView

        init {
            weekday = view.findViewById(R.id.weekday)
            weathericon = view.findViewById(R.id.weathericon)
            weekday_max = view.findViewById(R.id.weekday_max)
            weekday_min = view.findViewById(R.id.weekday_min)
        }
    }

    private fun handleWeekDay(timestamp : Long) : String {
        val calendar = Calendar.getInstance();
        calendar.timeInMillis = timestamp * 1000L
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date(calendar.timeInMillis))
    }

    private fun handleTempMetric(selectedUnit: String): String {
        return if (selectedUnit == "metric") "ºC" else "ºF"
    }


}