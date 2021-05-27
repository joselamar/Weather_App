package lamarao.jose.weatherapp.ui.cities

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomViewModelFactory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Cities_ViewModel::class.java)) {
            return Cities_ViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
