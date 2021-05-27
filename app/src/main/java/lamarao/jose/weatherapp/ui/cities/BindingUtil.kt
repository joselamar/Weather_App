package lamarao.jose.weatherapp.ui.cities

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import lamarao.jose.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.round

@BindingAdapter("CityTime")
fun TextView.handleTime(timestamp : Long) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp * 1000L
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    text=sdf.format(Date(calendar.timeInMillis))
}

@BindingAdapter ("CityWeather")
fun TextView.handleCityWeather(value : Double ){
    text = context.getString(R.string.city_weather_string,round(value).toInt().toString())
}

@BindingAdapter("CityWeatherIcon")
fun ImageView.handleCityWeatherIcon(icon : String ){
    setImageResource(when(icon){
        "01n" ->  R.drawable.ic_01n
        "01d" ->  R.drawable.ic_01d
        "02n" ->  R.drawable.ic_02n
        "02d" ->  R.drawable.ic_02d
        "03d" ->  R.drawable.ic_03d
        "03n" ->  R.drawable.ic_03n
        "04d" ->  R.drawable.ic_04d
        "04n" ->  R.drawable.ic_04n
        "09d" ->  R.drawable.ic_09d
        "09n" ->  R.drawable.ic_09n
        "10d" ->  R.drawable.ic_10d
        "10n" ->  R.drawable.ic_10n
        "11d" ->  R.drawable.ic_11d
        "11n" ->  R.drawable.ic_11n
        "13d" ->  R.drawable.ic_13d
        "13n" ->  R.drawable.ic_13n
        "50d" ->  R.drawable.ic_50d
        else -> R.drawable.ic_50n
    })
}

@BindingAdapter ("CityWeatherPressure")
fun TextView.handleCityWeatherPressure(value : Int ){
    text = context.getString(R.string.city_weather_pressure_string,value)
}

@BindingAdapter ("CityWeatherHumidity")
fun TextView.handleCityWeatherHumidity(value : Int ){
    text = context.getString(R.string.city_weather_humidity_string,value)
}

@BindingAdapter ("WindDegree","WindVelocity")
fun TextView.handleCityWeatherHumidity(deg : Double ,speed :Double ){
    text = context.getString(R.string.city_weather_wind, windDirection(deg),speed.toString())
}

private fun handleTempMetric(selectedUnit: String): String {
    return if (selectedUnit == "metric") "C" else "F"
}

private fun handleWindMetric(selectedUnit: String): String {
    return if (selectedUnit == "metric") " m/s" else " Mi/h"
}

private fun windDirection(deg: Double): String {
    var degree = deg
    val sectors = arrayOf("N","NE","E","SE","S","SW","W","NW")
    degree += 22.5
    if (degree < 0)
        degree = 360 - abs(degree) % 360
    else
        degree %= 360
    val which = (degree / 45).toInt()
    return sectors[which]
}
