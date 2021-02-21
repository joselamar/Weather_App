package lamarao.jose.wit.software.challenge.ui.Cities

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lamarao.jose.wit.software.challenge.api.Weather_Api
import lamarao.jose.wit.software.challenge.model.cities_weather_class
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class cities_ViewModel(Units: String) : ViewModel() {

    private val _cities_response = MutableLiveData<cities_weather_class>()

    val response: LiveData<cities_weather_class> get() = _cities_response

    init {
        startFetchingData(Units)
    }

    private fun getWeatherData_cities(unit: String) {
        Weather_Api.retrofitService.getWeatherData_cities(units = unit).enqueue(
            object : Callback<cities_weather_class> {
                override fun onFailure(call: Call<cities_weather_class>, t: Throwable) {
                    _cities_response.postValue(null)
                    Log.e("TAG", "Failure: " + t.message)
                }
                override fun onResponse(
                    call: Call<cities_weather_class>,
                    response: Response<cities_weather_class>
                ) {
                    _cities_response.postValue(response.body())
                    Log.e("TAG", response.body().toString())
                }
            }
        )
    }

    private fun startFetchingData(units: String) {
        val mainHandler = android.os.Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                getWeatherData_cities(units)
                mainHandler.postDelayed(this, 300000)
            }
        })
    }
}