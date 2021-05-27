package lamarao.jose.weatherapp.ui.current_Location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices
import lamarao.jose.weatherapp.database.Coord


class getLocation_LiveData(application: Application) : ViewModel() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(application.applicationContext)

    private val _location = MutableLiveData<Coord>()
    val location: LiveData<Coord> get() = _location


    init {
        getLocationlive(application.applicationContext)
    }

    private fun getLocationlive(context: Context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            _location.postValue(Coord(38.719785, -9.140806))
            Toast.makeText(context, "Loading other Location", Toast.LENGTH_LONG).show()
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location == null) {
                        _location.postValue(Coord(38.719785, -9.140806))
                        Toast.makeText(context, "Loading other Location", Toast.LENGTH_LONG).show()
                    } else {
                        _location.postValue(Coord(location.latitude, location.longitude))
                    }
                }
        }
    }



}