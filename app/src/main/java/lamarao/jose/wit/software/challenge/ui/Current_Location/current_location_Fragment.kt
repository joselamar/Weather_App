package lamarao.jose.wit.software.challenge.ui.Current_Location

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_current_location.*
import lamarao.jose.wit.software.challenge.R
import lamarao.jose.wit.software.challenge.adapter.rv_hourly_adapter
import lamarao.jose.wit.software.challenge.adapter.rv_weekday_adapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round
import kotlin.math.roundToInt


class current_location_Fragment() : Fragment() {

    private lateinit var currentlocationViewModel: current_location_ViewModel
    private var weekday_layoutManager: RecyclerView.LayoutManager? = null
    private var weekday_adapter : RecyclerView.Adapter<rv_weekday_adapter.ViewHolder>? = null
    private var hourly_layoutManager: RecyclerView.LayoutManager? = null
    private var hourly_adapter : RecyclerView.Adapter<rv_hourly_adapter.ViewHolder>? = null
    private lateinit var getLocationlivedata: getLocation_LiveData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_current_location, container, false)
    }

    @SuppressLint("WrongConstant", "SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val selectedUnit = sharedPref.getString("Unit", "metric")!!

        var lat = "38.719785"
        var lon = "-9.140806"
        val tempMetric : String = handleTempMetric(selectedUnit)
        val windMetric : String = handleWindMetric(selectedUnit)


        var factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return  getLocation_LiveData(activity!!.application) as T
            }
        }

        getLocationlivedata = ViewModelProvider(this,factory).get(getLocation_LiveData::class.java)

        getLocationlivedata.location.observe(viewLifecycleOwner, Observer {
            lat = it.lat.toString()
            lon = it.lon.toString()

            currentlocationViewModel = ViewModelProvider(this,CustomViewModelFactory(lat,lon,selectedUnit)).get(current_location_ViewModel::class.java)

            weekday_layoutManager = LinearLayoutManager(context)

            rv_weekday.layoutManager = weekday_layoutManager

            hourly_layoutManager = LinearLayoutManager(context, HORIZONTAL,false )
            rv_hour.layoutManager = hourly_layoutManager

            currentlocationViewModel.response.observe(viewLifecycleOwner, Observer {
                progress_bar.isVisible = false
                progressbar_info.isVisible = false
                divider.isVisible = true
                divider1.isVisible = true
                divider2.isVisible = true
                divider3.isVisible = true
                divider4.isVisible = true
                divider5.isVisible = true
                divider6.isVisible = true

                hourly_adapter = rv_hourly_adapter(it.hourly,selectedUnit)
                rv_hour.adapter = hourly_adapter

                weekday_adapter = rv_weekday_adapter(it.daily.subList(1,8),selectedUnit)
                rv_weekday.adapter = weekday_adapter

                location.text = getLocation(lat,lon)
                weather_state.text = it.current.weather[0].main

                temperature.text = round(it.current.temp).roundToInt().toString()+"º"
                temperature_metric.text = handleTempMetric(selectedUnit)
                week_day.text = handleWeekDay(it.current.dt)
                max_temp.text = round(it.daily.get(0).temp.max).roundToInt().toString()+tempMetric
                min_temp.text = round(it.daily.get(0).temp.min).roundToInt().toString()+tempMetric

                sunrise.text = "Sunrise"
                sunrise_time.text = handleTime(it.current.sunrise)

                sunset.text = "Sunset"
                sunset_time.text = handleTime(it.current.sunset)

                rain_prob.text = "Rain Probability"
                rain_prob_value.text = round(it.daily.get(0).pop*100).roundToInt().toString()+"%"

                humidity.text = "Humidity"
                humidity_value.text = it.current.humidity.toString()+"%"

                wind.text = "Wind"
                wind_value.text = windDirection(it.current.windDeg)+" "+it.current.windSpeed.toString()+windMetric

                temp_feeling.text = "Thermic Sensation"
                temp_feeling_value.text = round(it.current.feelsLike).roundToInt().toString()+"º"+tempMetric

                precipitation.text = "Precipitation"
                precipitation_value.text = (it.daily.get(0).rain).toString()+"mm"

                pressure.text = "Pressure"
                pressure_value.text = it.current.pressure.toString()+" hPA"

                visibility.text = "Visibility"
                visibility_value.text = (it.current.visibility*0.001).toString()+"Km"

                uv_index.text = "UV Index"
                uv_index_value.text = (it.daily.get(0).uvi).toString()

                current_icon.setImageResource(resources.getIdentifier("ic_"+it.current.weather[0].icon,"drawable",context?.packageName))
            })

        })


    }

    private fun handleWindMetric(selectedUnit: String): String {
        return if (selectedUnit == "metric") " m/s" else " Mi/h"
    }

    private fun handleTempMetric(selectedUnit: String): String {
        return if (selectedUnit == "metric") "C" else "F"
    }

    private fun handleTime(timestamp : Long) : String {
        val calendar = Calendar.getInstance();
        calendar.timeInMillis = timestamp * 1000L
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(calendar.timeInMillis))
    }

    private fun handleWeekDay(timestamp : Long) : String {
        val calendar = Calendar.getInstance();
        calendar.timeInMillis = timestamp * 1000L
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date(calendar.timeInMillis))
    }

    private fun windDirection(deg : Double): String {
        var degree= deg
        val sectors = arrayOf("N","NE","E","SE","S","SW","W","NW")
        degree += 22.5;
        if (degree < 0)
            degree = 360 - Math.abs(degree) % 360;
        else
            degree %= 360;
        val which = (deg / 45).toInt();
        return sectors[which]
    }

    private fun getLocation (lat : String, lon : String): String? {
        val gcd = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = gcd.getFromLocation(lat.toDouble(), lon.toDouble(), 1)
        return if (addresses.isNotEmpty()) {
            addresses[0].locality
        } else {
            "Location Unavailable"
        }
    }
}