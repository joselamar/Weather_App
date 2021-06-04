package lamarao.jose.weatherapp.ui.current_Location

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import kotlinx.coroutines.launch
import lamarao.jose.weatherapp.database.getDatabase
import lamarao.jose.weatherapp.repository.LocationRepository
import lamarao.jose.weatherapp.repository.WeatherRepository
import java.util.concurrent.Executors

class CurrentLocationViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)

    private val weatherRepository = WeatherRepository(database)

    private val locationRepository = LocationRepository(application,Executors.newSingleThreadExecutor())

    fun startLocationUpdates() = locationRepository.startLocationUpdates()

    fun stopLocationUpdates() = locationRepository.stopLocationUpdates()

    val userLocation = locationRepository.getLocation

    val currentLocationWeather = weatherRepository.currentLocation

    fun refreshCurrentLocationWeather(lat: String, lon: String) {
        viewModelScope.launch {
            weatherRepository.refreshCurrentLocationWeather(lat,lon,getUnits(getApplication()))
        }
    }

    private fun getUnits(application: Application): String {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(application)
        return sharedPref.getString("Unit", "metric")!!
    }
}