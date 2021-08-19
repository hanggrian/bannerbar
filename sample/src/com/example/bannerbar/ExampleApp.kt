package com.example.bannerbar

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.hendraanggrian.prefs.PreferencesLogger
import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.android.Android
import com.hendraanggrian.prefs.android.preferences

class ExampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Prefs.setLogger(PreferencesLogger.Android)
        val theme = preferences.getInt("theme")
        if (theme != null && theme != AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(theme)
        }
    }
}