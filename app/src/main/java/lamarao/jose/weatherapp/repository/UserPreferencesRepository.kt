package lamarao.jose.weatherapp.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException

private const val USER_PREFERENCES_NAME = "user_preferences"
private val Context.dataStore by preferencesDataStore( name = USER_PREFERENCES_NAME)


class UserPreferencesRepository(context: Context) {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val UNITS = stringPreferencesKey("units")
        val THEME = stringPreferencesKey("theme")
    }

    val readUnits: Flow<String> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Timber.e("Error reading preferences.$exception")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
           return@map preferences[PreferencesKeys.UNITS] ?: "metric"
        }

    val readTheme: Flow<String> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Timber.e("Error reading preferences.$exception")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            return@map preferences[PreferencesKeys.THEME] ?: "AppTheme"
        }

    suspend fun writeUnits(units : String) {
        dataStore.edit { preferences -> preferences[PreferencesKeys.UNITS]= units }
    }

    suspend fun writeTheme(theme : String) {
        dataStore.edit { preferences -> preferences[PreferencesKeys.THEME]= theme }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        fun getInstance(context: Context): UserPreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = UserPreferencesRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}

