package com.example.bannerbar

import android.os.Bundle
import android.util.Log
import androidx.annotation.ArrayRes
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.snackbar.Bannerbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.addCallback
import com.hendraanggrian.prefy.Prefy
import com.hendraanggrian.prefy.android.AndroidPreferences
import com.hendraanggrian.prefy.android.get

class ExampleFragment : PreferenceFragmentCompat() {

    companion object {
        const val TEXT = "You have lost connection to the internet. This app is offline."
        const val ACTION_TEXT1 = "Dismiss"
        const val ACTION_TEXT2 = "Turn on WiFi"
    }

    private lateinit var preferences: AndroidPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_example)
        preferences = Prefy[preferenceManager.sharedPreferences]

        findPreference<ListPreference>("duration")!!.bindSummary({ value }) {
            getActualString(it, R.array.duration_values, R.array.durations)
        }
        findPreference<ListPreference>("animationMode")!!.bindSummary({ value }) {
            getActualString(it, R.array.mode_values, R.array.modes)
        }

        findPreference<Preference>("show")!!.setOnPreferenceClickListener {
            val bannerbar = Bannerbar.make(view!!, TEXT, preferences["duration"]!!.toInt())
            if (preferences.getBoolean("showIcon")!!) {
                bannerbar.setIcon(R.drawable.ic_no_wifi)
            }
            repeat(preferences.getInt("actionCount")!!) {
                bannerbar.addAction(if (it == 0) ACTION_TEXT1 else ACTION_TEXT2) { Log.d("Prefy", "Clicked") }
            }
            bannerbar.animationMode = preferences["animationMode"]!!.toInt()
            bannerbar.addCallback {
                onShown { Log.d("Prefy", "Shown") }
                onDismissed { _, event -> Log.d("Prefy", "Dismissed event: $event") }
            }
            bannerbar.show()
            false
        }
        findPreference<Preference>("showSnackbar")!!.setOnPreferenceClickListener {
            Snackbar.make(view!!, TEXT, preferences["duration"]!!.toInt())
                .setAction(ACTION_TEXT1) { }
                .setAnimationMode(preferences["animationMode"]!!.toInt())
                .show()
            false
        }
    }

    /**
     * @param initial starting value can be obtained from its value, text, etc.
     * @param convert its preference value to representable summary text.
     */
    private fun <P : Preference, T> P.bindSummary(
        initial: P.() -> T?,
        convert: (T) -> CharSequence? = { it?.toString() }
    ) {
        initial()?.let { summary = convert(it) }
        setOnPreferenceChangeListener { preference, newValue ->
            @Suppress("UNCHECKED_CAST")
            preference.summary = convert(newValue as T)
            true
        }
    }

    private fun getActualString(
        s: CharSequence,
        @ArrayRes arrayValuesRes: Int,
        @ArrayRes arraysRes: Int
    ): CharSequence {
        val arrayValues = resources.getStringArray(arrayValuesRes)
        val arrays = resources.getStringArray(arraysRes)
        return arrays[arrayValues.indexOf(s)]
    }
}