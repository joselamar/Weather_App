package lamarao.jose.weatherapp.database

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class DataTypeConverters {

    var moshi: Moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromDailyToString(data: List<Daily>): String {
        var type: Type = Types.newParameterizedType(MutableList::class.java, Daily::class.java)
        val adapter: JsonAdapter<List<Daily>> = moshi.adapter(type)
        return adapter.toJson(data)
    }

    @TypeConverter
    fun toDailyFromString(json: String): List<Daily>? {
        var type: Type = Types.newParameterizedType(MutableList::class.java, Daily::class.java)
        val adapter: JsonAdapter<List<Daily>> = moshi.adapter(type)
        return adapter.fromJson(json)
    }

    @TypeConverter
    fun fromHourlyToString(data: List<Hourly>): String {
        var type: Type = Types.newParameterizedType(MutableList::class.java, Hourly::class.java)
        val adapter: JsonAdapter<List<Hourly>> = moshi.adapter(type)
        return adapter.toJson(data)
    }

    @TypeConverter
    fun toHourlyFromString(json: String): List<Hourly>? {
        var type: Type = Types.newParameterizedType(MutableList::class.java, Hourly::class.java)
        val adapter: JsonAdapter<List<Hourly>> = moshi.adapter(type)
        return adapter.fromJson(json)}

    @TypeConverter
    fun fromWeatherToString(data: List<Weather>): String {
        var type: Type = Types.newParameterizedType(MutableList::class.java, Weather::class.java)
        val adapter: JsonAdapter<List<Weather>> = moshi.adapter(type)
        return adapter.toJson(data)
    }

    @TypeConverter
    fun toWeatherFromString(json: String): List<Weather>? {
        var type: Type = Types.newParameterizedType(MutableList::class.java, Weather::class.java)
        val adapter: JsonAdapter<List<Weather>> = moshi.adapter(type)
        return adapter.fromJson(json)}

    @TypeConverter
    fun fromWeatherCitiesToString(data: List<Weather_Cities>): String {
        var type: Type = Types.newParameterizedType(MutableList::class.java, Weather_Cities::class.java)
        val adapter: JsonAdapter<List<Weather_Cities>> = moshi.adapter(type)
        return adapter.toJson(data)
    }

    @TypeConverter
    fun toWeatherCitiesFromString(json: String): List<Weather_Cities>? {
        var type: Type = Types.newParameterizedType(MutableList::class.java, Weather_Cities::class.java)
        val adapter: JsonAdapter<List<Weather_Cities>> = moshi.adapter(type)
        return adapter.fromJson(json)
    }

}
