package lamarao.jose.wit.software.challenge.api

import lamarao.jose.wit.software.challenge.model.Weather_Class
import lamarao.jose.wit.software.challenge.model.cities_weather_class
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "169f39b78fcfc3ecf2006610f53b1678"
private const val BASE_URL = "https://api.openweathermap.org"
private const val exclusions = "minutely,alerts"
private const val cities = "2267057,3117735,6455259,2950159,2618425,3169070,2643743,2964574,3067696,2761369"

//https://api.openweathermap.org/data/2.5/weather?lat=38.719785&lon=-9.140806&appid=169f39b78fcfc3ecf2006610f53b1678
//http://api.openweathermap.org/data/2.5/group?id=2267057,3117735,6455259,2950159,2618425,3169070,2643743,2964574,3067696,2761369&appid=169f39b78fcfc3ecf2006610f53b1678&units=metric

interface Weather_Api_Service {

    @GET("/data/2.5/onecall?")
    fun getWeatherData(@Query("lat") lat: String, @Query("lon") lon: String, @Query("units") units: String, @Query("exclude") exclude : String = exclusions, @Query("appid") appid: String = API_KEY): Call<Weather_Class>

    @GET("/data/2.5/group?")
    fun getWeatherData_cities(@Query("id") id: String = cities, @Query("units") units: String, @Query("appid") appid: String = API_KEY): Call<cities_weather_class>

}

private val retrofit = Retrofit.Builder()
    .addCallAdapterFactory(
        RxJava2CallAdapterFactory.create())
    .addConverterFactory(
        GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object Weather_Api {
    val retrofitService : Weather_Api_Service by lazy {
        retrofit.create(Weather_Api_Service::class.java) }
}
