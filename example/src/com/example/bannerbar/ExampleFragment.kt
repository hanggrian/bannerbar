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
        const val TITLE = "Mobile data is off"
        const val SUBTITLE = "No data connection. Consider turning on mobile data or turning on Wi-Fi."
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

        findPreference<Preference>("showBannerbar")!!.setOnPreferenceClickListener {
            val bannerbar = Bannerbar.make(view!!, TITLE, preferences["duration"]!!.toInt())
            if (preferences.getBoolean("showIcon")!!) {
                bannerbar.setIcon(R.drawable.ic_error)
            }
            if (preferences.getBoolean("showSubtitle")!!) {
                bannerbar.setSubtitle(SUBTITLE)
            }
            repeat(preferences.getInt("actionCount")!!) {
                bannerbar.addAction(if (it == 0) ACTION_TEXT1 else ACTION_TEXT2)
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
            val snacbar = Snackbar.make(view!!, TITLE, preferences["duration"]!!.toInt())
            snacbar.setAction(
                when (preferences.getInt("actionCount")!!) {
                    1 -> ACTION_TEXT1
                    2 -> ACTION_TEXT2
                    else -> null
                }
            ) { }
            snacbar.animationMode = preferences["animationMode"]!!.toInt()
            snacbar.show()
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