/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.settings

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import org.mozilla.fenix.R

class ThemeFragment : PreferenceFragmentCompat() {
    private lateinit var radioLightTheme: RadioButtonPreference
    private lateinit var radioDarkTheme: RadioButtonPreference
    private lateinit var radioAutoBatteryTheme: RadioButtonPreference
    private lateinit var radioFollowDeviceTheme: RadioButtonPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).title = getString(R.string.preferences_theme)
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.theme_preferences, rootKey)
    }

    override fun onResume() {
        super.onResume()
        setupPreferences()
    }

    private fun setupPreferences() {
        bindFollowDeviceTheme()
        bindDarkTheme()
        bindLightTheme()
        bindAutoBatteryTheme()
        setupRadioGroups()
    }

    private fun setupRadioGroups() {
        radioLightTheme.addToRadioGroup(radioDarkTheme)

        radioDarkTheme.addToRadioGroup(radioLightTheme)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            radioLightTheme.addToRadioGroup(radioFollowDeviceTheme)
            radioDarkTheme.addToRadioGroup(radioFollowDeviceTheme)

            radioFollowDeviceTheme.addToRadioGroup(radioDarkTheme)
            radioFollowDeviceTheme.addToRadioGroup(radioLightTheme)
        } else {
            radioLightTheme.addToRadioGroup(radioAutoBatteryTheme)
            radioDarkTheme.addToRadioGroup(radioAutoBatteryTheme)

            radioAutoBatteryTheme.addToRadioGroup(radioLightTheme)
            radioAutoBatteryTheme.addToRadioGroup(radioDarkTheme)
        }
    }

    private fun bindLightTheme() {
        val keyLightTheme = getString(R.string.pref_key_light_theme)
        radioLightTheme = requireNotNull(findPreference(keyLightTheme))
        radioLightTheme.onClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            activity?.recreate()
        }
    }

    @SuppressLint("WrongConstant")
    // Suppressing erroneous lint warning about using MODE_NIGHT_AUTO_BATTERY, a likely library bug
    private fun bindAutoBatteryTheme() {
        val keyBatteryTheme = getString(R.string.pref_key_auto_battery_theme)
        radioAutoBatteryTheme = requireNotNull(findPreference(keyBatteryTheme))
        radioAutoBatteryTheme.onClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            activity?.recreate()
        }
    }

    private fun bindDarkTheme() {
        val keyDarkTheme = getString(R.string.pref_key_dark_theme)
        radioDarkTheme = requireNotNull(findPreference(keyDarkTheme))
        radioDarkTheme.onClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            activity?.recreate()
        }
    }

    private fun bindFollowDeviceTheme() {
        val keyDeviceTheme = getString(R.string.pref_key_follow_device_theme)
        radioFollowDeviceTheme = requireNotNull(findPreference(keyDeviceTheme))
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            radioFollowDeviceTheme.onClickListener {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                activity?.recreate()
            }
        }
    }
}
