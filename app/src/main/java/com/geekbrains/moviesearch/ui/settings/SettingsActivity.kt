package com.geekbrains.moviesearch.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.databinding.SettingsActivityBinding
import com.geekbrains.moviesearch.ui.setGlobalNightMode

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            preferenceManager.findPreference<SwitchPreferenceCompat>(getString(R.string.pref_dark_theme))
                ?.apply {
                    onPreferenceChangeListener =
                        Preference.OnPreferenceChangeListener { preference: Preference?, newValue: Any ->
                            setGlobalNightMode(newValue as Boolean)
                            true
                        }
                }

            preferenceManager.findPreference<SwitchPreferenceCompat>(getString(R.string.pref_adult_key))
                ?.apply {
                    onPreferenceChangeListener =
                        Preference.OnPreferenceChangeListener { preference: Preference?, newValue: Any ->
                            sharedPreferences.edit()
                                .putBoolean(getString(R.string.pref_adult_key), newValue as Boolean)
                                .apply()
                            true
                        }
                }
            return super.onCreateView(inflater, container, savedInstanceState)
        }
    }
}