package lamarao.jose.weatherapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import lamarao.jose.weatherapp.database.Weather_Class
import lamarao.jose.weatherapp.database.cities_weather_class
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "169f39b78fcfc3ecf2006610f53b1678"
private const val BASE_URL = "https://api.openweathermap.org"
private const val exclusions = "minutely,alerts"
private const val cities = "2267057,3117735,6455259,2950159,2618425,3169070,2643743,2964574,3067696,2761369"

//https://api.openweathermap.org/data/2.5/onecall?lat=38.719785&lon=-9.140806&appid=169f39b78fcfc3ecf2006610f53b1678&exclude=minutely,alerts
//http://api.openweathermap.org/data/2.5/group?id=2267057,3117735,6455259,2950159,2618425,3169070,2643743,2964574,3067696,2761369&appid=169f39b78fcfc3ecf2006610f53b1678&units=metric

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface Weather_Api_Service {

    @GET("/data/2.5/onecall?")
    fun getWeatherData(@Query("lat") lat: String, @Query("lon") lon: String, @Query("units") units: String, @Query("exclude") exclude : String = exclusions, @Query("appid") appid: String = API_KEY): Deferred<Weather_Class>

    @GET("/data/2.5/group?")
    fun getWeatherData_cities(@Query("id") id: String = cities, @Query("units") units: String, @Query("appid") appid: String = API_KEY): Deferred<cities_weather_class>

}

object Weather_Api {
    val retrofitService : Weather_Api_Service by lazy {
        retrofit.create(Weather_Api_Service::class.java) }
}
