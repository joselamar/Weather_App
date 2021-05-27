package lamarao.jose.weatherapp.ui.current_Location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomViewModelFactory(private val lat: String, private val lon: String, private val units : String) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return current_location_ViewModel(lat,lon,units) as T
    }
}
