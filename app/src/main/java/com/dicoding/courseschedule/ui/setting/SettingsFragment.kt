package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode

class SettingsFragment : PreferenceFragmentCompat() {

    private val dailyReminder = DailyReminder()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        var prefUpdateTheme = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        prefUpdateTheme?.setOnPreferenceChangeListener{ preference, newValue ->
            when(newValue as String){
                "auto" -> updateTheme(NightMode.AUTO.value)
                "on" -> updateTheme(NightMode.ON.value)
                "off" -> updateTheme(NightMode.OFF.value)
            }
            true
        }

        var prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        prefNotification?.setOnPreferenceChangeListener{ preference, newValue ->
            val channelName = getString(R.string.pref_notify_name)
            if(newValue == true) {
                dailyReminder.setDailyReminder(requireContext())
            } else{
                dailyReminder.cancelAlarm(requireContext())
            }
            true
        }


    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}