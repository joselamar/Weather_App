package lamarao.jose.wit.software.challenge.ui.Current_Location

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
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import lamarao.jose.wit.software.challenge.model.Coord


class getLocation_LiveData(application: Application) : ViewModel() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(application.applicationContext)

    private val _location = MutableLiveData<Coord>()
    val location: LiveData<Coord> get() = _location

    private val locationRequest : LocationRequest = LocationRequest.create().apply { interval= 30000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            if (locationResult == null) {
                Log.e("TAG","1")
                _location.postValue(Coord(38.719785,-9.140806))
            } else {
                for (location in locationResult.locations) {
                    _location.postValue(Coord(location.latitude,location.longitude))
                    Log.e("TAG","2")
                    Log.e("TAG", location.latitude.toString()+location.longitude.toString())
                }
            }
        }
    }

    init {
        startLocationUpdates(application.applicationContext)
        getLocationlive(application.applicationContext)
    }

    private fun getLocationlive(context: Context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            _location.postValue(Coord(38.719785, -9.140806))
            Toast.makeText(context, "Loading other Location", Toast.LENGTH_LONG).show()
            Log.e("TAG", "imhere")
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location == null) {
                        _location.postValue(Coord(38.719785, -9.140806))
                        Toast.makeText(context, "Loading other Location", Toast.LENGTH_LONG).show()
                        Log.e("TAG", "imhere1")
                    } else {
                        _location.postValue(Coord(location.latitude, location.longitude))
                        Log.e("TAG", location.latitude.toString() + location.longitude)
                    }
                }
        }
    }

        fun startLocationUpdates(context: Context) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) { _location.postValue(Coord(38.719785,-9.140806)) }
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
            Log.e("TAG","3")
        }

    }