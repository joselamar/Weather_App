package lamarao.jose.weatherapp.ui.current_Location

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lamarao.jose.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*


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


        })


    }

    private fun handleWindMetric(selectedUnit: String): String {
        return if (selectedUnit == "metric") " m/s" else " Mi/h"
    }

    private fun handleTempMetric(selectedUnit: String): String {
        return if (selectedUnit == "metric") "C" else "F"
    }

    private fun handleTime(timestamp : Long) : String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp * 1000L
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(calendar.timeInMillis))
    }

    private fun handleWeekDay(timestamp : Long) : String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp * 1000L
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date(calendar.timeInMillis))
    }

    private fun windDirection(deg : Double): String {
        var degree = deg
        val sectors = arrayOf("N","NE","E","SE","S","SW","W","NW")
        degree += 22.5
        if (deg < 0)
            degree = 360 - kotlin.math.abs(degree) % 360
        else
            degree %= 360
        val which = (degree / 45).toInt()
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