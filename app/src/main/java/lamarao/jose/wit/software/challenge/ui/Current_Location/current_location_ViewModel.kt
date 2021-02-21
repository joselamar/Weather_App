package lamarao.jose.wit.software.challenge.ui.Current_Location

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lamarao.jose.wit.software.challenge.api.Weather_Api
import lamarao.jose.wit.software.challenge.model.Weather_Class
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Handler


class current_location_ViewModel(lat: String, lon: String, units: String) : ViewModel() {

    private val _response = MutableLiveData<Weather_Class>()

    val response: LiveData<Weather_Class> get() = _response

    init {
        startFetchingData(lat, lon, units)
    }

    private fun getCurrentLocationWeather(lat : String, lon : String, units : String) {
        Weather_Api.retrofitService.getWeatherData(lat,lon,units).enqueue(
            object : Callback<Weather_Class> {
                override fun onFailure(call: Call<Weather_Class>, t: Throwable) {
                    _response.postValue(null)
                    Log.e("TAG", "Failure: " + t.message)
                }
                override fun onResponse(
                    call: Call<Weather_Class>,
                    response: Response<Weather_Class>
                ) {
                    _response.postValue(response.body())
                    Log.e("TAG", response.body().toString())
                }
            }
        )
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