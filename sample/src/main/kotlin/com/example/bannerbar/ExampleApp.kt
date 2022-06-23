package com.example.bannerbar

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.hendraanggrian.auto.prefs.PreferencesLogger
import com.hendraanggrian.auto.prefs.Prefs
import com.hendraanggrian.auto.prefs.android.Android
import com.hendraanggrian.auto.prefs.android.preferences

class ExampleApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Prefs.setLogger(PreferencesLogger.Android)
        val theme = preferences.getInt("theme")
        if (theme != null && theme != AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(theme)
        }
    }
}
