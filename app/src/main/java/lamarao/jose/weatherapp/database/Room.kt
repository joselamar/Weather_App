package lamarao.jose.weatherapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeatherDao {

    @Query("select * from UserLocation")
    fun getUserLocation(): LiveData<UserLocation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserLocation(vararg currentLocation: UserLocation)

    @Query("select * from Weather_Class")
    fun getWeatherCurrentLocation(): LiveData<Weather_Class>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentLocation(vararg currentLocation: Weather_Class)

    @Query("select * from cities_weather_class")
    fun getWeatherCities(): LiveData<cities_weather_class>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(vararg cities: cities_weather_class)
}

@Database(entities = [Weather_Class::class, cities_weather_class::class, UserLocation::class], version = 2)
@TypeConverters(DataTypeConverters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDao : WeatherDao
}

private lateinit var INSTANCE: WeatherDatabase

fun getDatabase(context: Context): WeatherDatabase {
    synchronized(WeatherDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                WeatherDatabase::class.java,
                "weather_database.db").fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}