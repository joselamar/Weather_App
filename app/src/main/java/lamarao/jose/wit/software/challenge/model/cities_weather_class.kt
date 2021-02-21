package lamarao.jose.wit.software.challenge.model


import com.google.gson.annotations.SerializedName

data class cities_weather_class(
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("list")
    val list: List<Weather_Class>
)