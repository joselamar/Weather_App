package lamarao.jose.weatherapp.ui

import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import lamarao.jose.weatherapp.R
import lamarao.jose.weatherapp.database.Weather_Class
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.roundToInt


@BindingAdapter("Text")
fun TextView.handleText(value : String?) {
    text = value ?: ""
}

@BindingAdapter("Time")
fun TextView.handleTimeValue(timestamp : Long?) {
    text = if (timestamp==null) "" else handleTime(timestamp,"HH:mm")
}

@BindingAdapter ("Temp")
fun TextView.handleTemp(value : Double? ){
    text = if (value==null) ""  else context.getString(R.string.city_weather_string,round(value).toInt().toString())
}

@BindingAdapter("WeatherIcon")
fun ImageView.handleWeatherIcon(icon : String?){
    if (icon.isNullOrBlank()){
        setImageResource(R.drawable.ic_01d)
    } else {
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
}

@BindingAdapter ("WeatherPressure")
fun TextView.handleWeatherPressure(value : Int? ){
    text = if (value==null) "" else context.getString(R.string.city_weather_pressure_string,value)
}

@BindingAdapter ("WeatherHumidity")
fun TextView.handleWeatherHumidity(value : Int? ){
    text = if (value==null) "" else context.getString(R.string.city_weather_humidity_string,value)
}

@BindingAdapter ("RainProb")
fun TextView.handleRainProb(value : Double? ){
    text = if (value==null) "" else context.getString(R.string.city_weather_humidity_string,round(value*100).roundToInt())
}

@BindingAdapter ("WindDegree","WindVelocity","WindMetric")
fun TextView.handleWind(deg : Double? ,speed :Double?, selectedUnit: String? ){
    text = if (deg==null || speed==null || selectedUnit==null) "" else context.getString(R.string.city_weather_wind, windDirection(deg),speed.toString(), handleWindMetric(selectedUnit!!))
}

@BindingAdapter ("TempAndMetric","Unit")
fun TextView.handleTemperatureAndMetric(value : Double? , selectedUnit: String? ){
    text = if (value==null || selectedUnit==null) "" else context.getString(R.string.current_location_thermal_sensation_value, round(value).toInt().toString(), handleTempMetric(selectedUnit!!))
}

@BindingAdapter("WeatherMetric")
fun TextView.weatherTempMetric(selectedUnit: String?) {
    text = if (selectedUnit == "metric") "C" else "F"
}

@BindingAdapter("WeekDay")
fun TextView.setWeekDay(timestamp : Long?) {
    text = if (timestamp==null) "" else handleTime(timestamp,"EEEE")
}

@BindingAdapter("Hour", "Index")
fun TextView.setHour(value: Long?, index : Int?) {
    if (index==null || value==null){
        text = ""
    } else if(index==0){
        text = context.getString(R.string.current_location_hour)
        setTypeface(null, Typeface.BOLD)
    } else text = handleTime(value, "HH")
}

@BindingAdapter("HourRain")
fun TextView.setHourRain(value: Double?) {
    if (value==null){
        text = ""
    } else {
        if (value>=0.30){
            text = context.getString(R.string.current_location_hour_rain,round(value).toInt().toString())
            setTextColor(Color.rgb(51,170,255))
        } else {
            text = ""
        }
    }
}

@BindingAdapter ("Precipitation")
fun TextView.handlePrecipitation(value : Double? ){
    text = if (value==null) context.getString(R.string.current_location_precipitation_value,"0")  else context.getString(R.string.current_location_precipitation_value,round(value).toInt().toString())
}

@BindingAdapter ("VisibilityValue")
fun TextView.handleVisibility(value : Int? ){
    text = if (value==null) "" else context.getString(R.string.current_location_visibility_value,round(value*0.001).toInt().toString())
}

@BindingAdapter ("UVI")
fun TextView.handleUVI(value : Double? ){
    text = if (value==null) "" else context.getString(R.string.current_location_uv_index_value,round(value).toInt().toString())
}

@BindingAdapter("LoadingImage")
fun ImageView.handleLoading(value : String?) {
    visibility = if (value != null) View.GONE else View.VISIBLE
}


