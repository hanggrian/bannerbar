package com.hendraanggrian.material.errorbar.demo

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.ArrayRes
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.snackbar.Snackbar
import com.hendraanggrian.material.errorbar.Errorbar
import com.hendraanggrian.material.errorbar.addCallback

class DemoFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_demo)
        find<ListPreference>("duration").bindSummary({ value }) {
            getActualString(it, R.array.duration_values, R.array.durations)
        }
        find<ListPreference>("mode").bindSummary({ value }) {
            getActualString(it, R.array.mode_values, R.array.modes)
        }
        find<Preference>("show").setOnPreferenceClickListener {
            val preferences = preferenceManager.sharedPreferences
            Errorbar.make(view!!, "No internet connection.", preferences.getInt("duration"))
                .setAnimationMode(preferences.getInt("mode"))
                .setAction("Retry") {
                    Snackbar.make(view!!, "Clicked.", Snackbar.LENGTH_SHORT).show()
                }
                .addCallback {
                    onShown {
                        Toast.makeText(context, "shown", Toast.LENGTH_SHORT).show()
                    }
                    onDismissed { _, event ->
                        Toast.makeText(context, "Dismissed event: $event", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                .show()
            false
        }
    }

    @Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
    private inline fun <T : Preference> find(key: CharSequence): T =
        findPreference(key) as T

    private inline fun <T : Preference> find(key: CharSequence, block: T.() -> Unit): T =
        find<T>(key).apply(block)

    /**
     * @param initial starting value can be obtained from its value, text, etc.
     * @param convert its preference value to representable summary text.
     */
    private fun <P : Preference, T> P.bindSummary(
        initial: P.() -> T?,
        convert: (T) -> CharSequence? = { it?.toString() }
    ) {
        initial()?.let { summary = convert(it) }
        onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
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

    @Suppress("NOTHING_TO_INLINE")
    private inline fun SharedPreferences.getInt(key: String): Int =
        getString(key, "")!!.toInt()
}