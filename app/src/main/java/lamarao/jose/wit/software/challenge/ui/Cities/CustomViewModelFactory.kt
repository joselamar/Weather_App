package lamarao.jose.wit.software.challenge.ui.Cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomViewModelFactory(private val units : String) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return cities_ViewModel(units) as T
    }
}
