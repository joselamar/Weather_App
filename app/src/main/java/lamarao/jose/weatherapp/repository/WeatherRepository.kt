package lamarao.jose.weatherapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lamarao.jose.weatherapp.database.WeatherDatabase
import lamarao.jose.weatherapp.database.Weather_Class
import lamarao.jose.weatherapp.database.cities_weather_class
import lamarao.jose.weatherapp.network.Weather_Api

class WeatherRepository(private val database : WeatherDatabase) {

    val currentLocation: LiveData<Weather_Class> = database.weatherDao.getWeatherCurrentLocation()

    suspend fun refreshCurrentLocationWeather(lat: String, lon: String, unit: String){
        withContext(Dispatchers.IO) {
            val currentLocation = Weather_Api.retrofitService.getWeatherData(lat,lon,unit).await()
            currentLocation.unit = unit
            database.weatherDao.insertCurrentLocation(currentLocation)
        }
    }

    val citiesWeather : LiveData<cities_weather_class> = database.weatherDao.getWeatherCities()

    suspend fun refreshCitiesLocationWeather(unit: String){
        withContext(Dispatchers.IO) {
            try{
                val citiesWeather = Weather_Api.retrofitService.getWeatherData_cities(units = unit).await()
                (citiesWeather.list).forEach{ it.unit = unit}
                database.weatherDao.insertCities(citiesWeather)

            } catch (e: Exception) {
                Log.e("TAG","refreshVideos() error = "+e.message)
            }
        }
    }
}