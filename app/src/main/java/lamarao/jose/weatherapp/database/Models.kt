package lamarao.jose.weatherapp.database


import androidx.room.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Entity
@JsonClass(generateAdapter = true)
data class Weather_Class(
    @Json(name = "current")
    @Embedded val current: Current,
    @Json(name = "daily")
    val daily: List<Daily>,
    @Json(name = "hourly")
    val hourly: List<Hourly>,
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lon")
    val lon: Double,
    @PrimaryKey
    @Json(name = "timezone")
    val timezone: String,
    @Json(name = "timezone_offset")
    val timezoneOffset: Int,
    var unit : String?
)

@JsonClass(generateAdapter = true)
data class Daily(
    @Json(name = "clouds")
    val clouds: Int,
    @Json(name = "dew_point")
    val dewPoint: Double,
    @Json(name = "dt")
    val dt: Long,
    @Json(name = "feels_like")
    @Embedded val feelsLike: FeelsLike,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "moon_phase")
    val moonPhase: Double,
    @Json(name = "moonrise")
    val moonrise: Int,
    @Json(name = "moonset")
    val moonset: Int,
    @Json(name = "pop")
    val pop: Double,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "sunrise")
    val sunrise: Int,
    @Json(name = "sunset")
    val sunset: Int,
    @Json(name = "temp")
    @Embedded val temp: Temp,
    @Json(name = "uvi")
    val uvi: Double,
    @Json(name = "weather")
    val weather: List<Weather>,
    @Json(name = "wind_deg")
    val windDeg: Int,
    @Json(name = "wind_gust")
    val windGust: Double?,
    @Json(name = "wind_speed")
    val windSpeed: Double,
    @Json(name = "rain")
    val rain: Double?
)

@JsonClass(generateAdapter = true)
data class FeelsLike(
    @Json(name = "day")
    val day: Double,
    @Json(name = "eve")
    val eve: Double,
    @Json(name = "morn")
    val morn: Double,
    @Json(name = "night")
    val night: Double
)

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "deg")
    val deg: Double,
    @Json(name = "speed")
    val speed: Double
)

@Entity
@JsonClass(generateAdapter = true)
data class cities_weather_class(
    @PrimaryKey (autoGenerate = false)
    @Json(name = "cnt")
    val cnt: Int,
    @Json(name = "list")
    val list: List<Weather_Cities>
)

@JsonClass(generateAdapter = true)
data class Weather_Cities(
    @Json(name = "clouds")
    @Embedded val clouds: Clouds,
    @Json(name = "coord")
    @Embedded val coord: Coord,
    @Json(name = "dt")
    val dt: Long,
    @Json(name = "id")
    val id: Int,
    @Json(name = "main")
    @Embedded val main: Main,
    @Json(name = "name")
    val name: String,
    @Json(name = "sys")
    @Embedded val sys: Sys,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "weather")
    val weather: List<Weather>,
    @Json(name = "wind")
    @Embedded val wind: Wind,
    var unit: String?
)

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "description")
    val description: String,
    @Json(name = "icon")
    val icon: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "main")
    val main: String
)

@JsonClass(generateAdapter = true)
data class Temp(
    @Json(name = "day")
    val day: Double,
    @Json(name = "eve")
    val eve: Double,
    @Json(name = "max")
    val max: Double,
    @Json(name = "min")
    val min: Double,
    @Json(name = "morn")
    val morn: Double,
    @Json(name = "night")
    val night: Double
)

@JsonClass(generateAdapter = true)
data class Sys(
    @Json(name = "country")
    val country: String,
    @Json(name = "sunrise")
    val sunrise: Int,
    @Json(name = "sunset")
    val sunset: Int,
    @Json(name = "timezone")
    val timezone: Int
)

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "feels_like")
    val feelsLike: Double,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "temp")
    val temp: Double,
    @Json(name = "temp_max")
    val tempMax: Double,
    @Json(name = "temp_min")
    val tempMin: Double
)

@JsonClass(generateAdapter = true)
data class Hourly(
    @Json(name = "clouds")
    val clouds: Int,
    @Json(name = "dew_point")
    val dewPoint: Double,
    @Json(name = "dt")
    val dt: Long,
    @Json(name = "feels_like")
    val feelsLike: Double,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "pop")
    val pop: Double,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "temp")
    val temp: Double,
    @Json(name = "uvi")
    val uvi: Double,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "weather")
    val weather: List<Weather>,
    @Json(name = "wind_deg")
    val windDeg: Int,
    @Json(name = "wind_gust")
    val windGust: Double?,
    @Json(name = "wind_speed")
    val windSpeed: Double
)

@JsonClass(generateAdapter = true)
data class Current(
    @Json(name = "clouds")
    val clouds: Int,
    @Json(name = "dew_point")
    val dewPoint: Double,
    @Json(name = "dt")
    val dt: Long,
    @Json(name = "feels_like")
    val feelsLike: Double,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "sunrise")
    val sunrise: Long,
    @Json(name = "sunset")
    val sunset: Long,
    @Json(name = "temp")
    val temp: Double,
    @Json(name = "uvi")
    val uvi: Double,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "weather")
    val weather: List<Weather>,
    @Json(name = "wind_deg")
    val windDeg: Double,
    @Json(name = "wind_gust")
    val windGust: Double?,
    @Json(name = "wind_speed")
    val windSpeed: Double
)

@JsonClass(generateAdapter = true)
data class Coord(
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lon")
    val lon: Double
)

@JsonClass(generateAdapter = true)
data class Clouds(
    @Json(name = "all")
    val all: Int
)