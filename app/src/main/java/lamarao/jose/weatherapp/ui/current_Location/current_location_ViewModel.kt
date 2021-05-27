package lamarao.jose.weatherapp.ui.current_Location

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lamarao.jose.weatherapp.network.Weather_Api
import lamarao.jose.weatherapp.database.Weather_Class

enum class API_STATUS { LOADING, ERROR, DONE  }

class current_location_ViewModel(lat: String, lon: String, units: String) : ViewModel() {

    private val _response = MutableLiveData<Weather_Class>()

    val response: LiveData<Weather_Class>
        get() = _response

    private val _status = MutableLiveData<API_STATUS>()

    val status: LiveData<API_STATUS>
        get() = _status

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        startFetchingData(lat, lon, units)
    }

    private fun getCurrentLocationWeather(lat : String, lon : String, units : String) {
        coroutineScope.launch {
            val getLocalWeatherDeferred  = Weather_Api.retrofitService.getWeatherData(lat,lon,units)
            try {
                _status.value = API_STATUS.LOADING
                val result = getLocalWeatherDeferred.await()
                _status.value = API_STATUS.DONE
                _response.postValue(result)

            } catch (t:Throwable){
                _status.value = API_STATUS.ERROR
                _response.value = null
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun startFetchingData(lat:String, lon:String, units: String) {
        val mainHandler = android.os.Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                getCurrentLocationWeather(lat,lon,units)
                mainHandler.postDelayed(this, 300000)
            }
        })
    }

}