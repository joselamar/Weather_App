package lamarao.jose.wit.software.challenge.model


import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class Weather_Class(
    @SerializedName("current")
    val current: Current,
    @SerializedName("daily")
    val daily: List<Daily>,
    @SerializedName("hourly")
    val hourly: List<Hourly>,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int,
    @Embedded(prefix = "clouds_")
    @SerializedName("clouds")
    val clouds: Clouds,
    @Embedded(prefix = "coord_")
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
)