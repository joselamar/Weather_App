package lamarao.jose.weatherapp.ui.cities

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import kotlinx.coroutines.launch
import lamarao.jose.weatherapp.database.getDatabase
import lamarao.jose.weatherapp.repository.WeatherRepository

enum class API_STATUS { LOADING, ERROR, DONE  }

class Cities_ViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<API_STATUS>()
    val status: LiveData<API_STATUS>
        get() = _status

    private val database = getDatabase(application)
    private val weatherRepository = WeatherRepository(database)

    init {
        viewModelScope.launch {
            weatherRepository.refreshCitiesLocationWeather(getUnits(application))
        }
    }

    var citiesWeather= weatherRepository.citiesWeather


    private fun getUnits(application: Application): String {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(application)
        return sharedPref.getString("Unit", "metric")!!
    }


}