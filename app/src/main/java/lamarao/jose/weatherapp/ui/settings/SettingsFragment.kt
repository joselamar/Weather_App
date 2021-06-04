package lamarao.jose.weatherapp.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import lamarao.jose.weatherapp.R
import lamarao.jose.weatherapp.repository.UserPreferencesRepository

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.root_preferences)
    }
}