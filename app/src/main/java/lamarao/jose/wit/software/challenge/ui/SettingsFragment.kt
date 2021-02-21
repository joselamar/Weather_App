package lamarao.jose.wit.software.challenge.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import lamarao.jose.wit.software.challenge.MainActivity
import lamarao.jose.wit.software.challenge.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.root_preferences)
    }
}