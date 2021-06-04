package lamarao.jose.weatherapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationResult
import lamarao.jose.weatherapp.database.UserLocation
import lamarao.jose.weatherapp.repository.LocationRepository
import timber.log.Timber
import java.util.*
import java.util.concurrent.Executors


class LocationBroadcastReceiver : BroadcastReceiver()  {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == ACTION_PROCESS_UPDATES) {

            // Checks for location availability changes.
            LocationAvailability.extractLocationAvailability(intent)?.let { locationAvailability ->
                if (!locationAvailability.isLocationAvailable) {
                    Timber.e("Location services are no longer available!")
                }
            }

            LocationResult.extractResult(intent)?.let { locationResult ->
                val locations = locationResult.locations.map { location ->
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    val cityName: String = addresses[0].locality
                    UserLocation(
                        index = 1,
                        lat = location.latitude.toString(),
                        lon = location.longitude.toString(),
                        locality = cityName
                    )
                }
                if (locations.isNotEmpty()) {
                    LocationRepository(context, Executors.newSingleThreadExecutor()).addLocation(locations[0])
                }
            }
        }
    }

    companion object { const val ACTION_PROCESS_UPDATES = "lamarao.jose.weatherapp.action." + "PROCESS_UPDATES" }
}