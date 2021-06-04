package lamarao.jose.weatherapp.ui.current_Location

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CustomViewModelFactory(private val application : Application) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentLocationViewModel::class.java)) {
            return CurrentLocationViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
