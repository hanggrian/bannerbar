package com.example.bannerbar

import android.os.Bundle
import androidx.annotation.ArrayRes
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class MainFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_main)
        findPreference<ListPreference>("duration")!!.bindSummary({ value }) {
            getActualString(it, R.array.duration_values, R.array.durations)
        }
        findPreference<ListPreference>("animationMode")!!.bindSummary({ value }) {
            getActualString(it, R.array.mode_values, R.array.modes)
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
