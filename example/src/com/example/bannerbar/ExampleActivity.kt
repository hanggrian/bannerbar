package com.example.bannerbar

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Bannerbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.addCallback
import com.hendraanggrian.prefy.BindPreference
import com.hendraanggrian.prefy.PreferencesSaver
import com.hendraanggrian.prefy.Prefy
import com.hendraanggrian.prefy.android.AndroidPreferences
import com.hendraanggrian.prefy.android.get
import com.hendraanggrian.prefy.bind
import com.jakewharton.processphoenix.ProcessPhoenix
import kotlinx.android.synthetic.main.activity_example.*

class ExampleActivity : AppCompatActivity() {

    companion object {
        const val TITLE = "Mobile data is off"
        const val SUBTITLE = "No data connection. Consider turning on mobile data or turning on Wi-Fi."
        const val ACTION_TEXT1 = "Dismiss"
        const val ACTION_TEXT2 = "Turn on WiFi"
    }

    private lateinit var preferences: AndroidPreferences
    private lateinit var saver: PreferencesSaver

    @JvmField @BindPreference("theme") var theme2 = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    @BindPreference("duration") lateinit var duration: String
    @JvmField @BindPreference("showIcon") var showIcon = false
    @JvmField @BindPreference("showSubtitle") var showSubtitle = false
    @JvmField @BindPreference("actionCount") var actionCount = 0
    @BindPreference("animationMode") lateinit var animationMode: String
    @JvmField @BindPreference("titleColor") var titleColor2 = 0
    @JvmField @BindPreference("subtitleColor") var subtitleColor = 0
    @JvmField @BindPreference("actionTextColors") var actionTextColors = 0
    @JvmField @BindPreference("backgroundTint") var backgroundTint = 0

    @ColorInt private var transparent = Color.TRANSPARENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.frameLayout, ExampleFragment())
            .commitNow()
        preferences = Prefy[this]
        saver = preferences.bind(this)

        fab.setOnClickListener {
            preferences.bind(this)
            val bannerbar = Bannerbar.make(fab, TITLE, duration.toInt())
            if (showIcon) bannerbar.setIcon(R.drawable.ic_error)
            if (showSubtitle) bannerbar.setSubtitle(SUBTITLE)
            repeat(actionCount) {
                bannerbar.addAction(if (it == 0) ACTION_TEXT1 else ACTION_TEXT2)
            }
            bannerbar.animationMode = animationMode.toInt()
            if (titleColor2 != transparent) bannerbar.setTitleColor(titleColor2)
            if (subtitleColor != transparent) bannerbar.setSubtitleColor(subtitleColor)
            if (actionTextColors != transparent) bannerbar.setActionTextColors(actionTextColors)
            if (backgroundTint != transparent) bannerbar.setBackgroundTint(backgroundTint)
            bannerbar.addCallback {
                onShown { Log.d("Bannerbar", "Shown") }
                onDismissed { _, event -> Log.d("Bannerbar", "Dismissed event: $event") }
            }
            bannerbar.show()
        }
        fab.setOnLongClickListener {
            preferences.bind(this)
            val snackbar = Snackbar.make(fab, TITLE, duration.toInt())
            snackbar.setAction(
                when (actionCount) {
                    1 -> ACTION_TEXT1
                    2 -> ACTION_TEXT2
                    else -> null
                }
            ) { }
            snackbar.animationMode = animationMode.toInt()
            if (titleColor2 != transparent) snackbar.setTextColor(titleColor2)
            if (actionTextColors != transparent) snackbar.setActionTextColor(actionTextColors)
            if (backgroundTint != transparent) snackbar.setBackgroundTint(backgroundTint)
            snackbar.show()
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_example, menu)
        menu.findItem(
            when (theme2) {
                AppCompatDelegate.MODE_NIGHT_NO -> R.id.lightThemeItem
                AppCompatDelegate.MODE_NIGHT_YES -> R.id.darkThemeItem
                else -> R.id.defaultThemeItem
            }
        ).isChecked = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.defaultThemeItem, R.id.lightThemeItem, R.id.darkThemeItem -> {
                theme2 = when (item.itemId) {
                    R.id.lightThemeItem -> AppCompatDelegate.MODE_NIGHT_NO
                    R.id.darkThemeItem -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
                saver.save()
                AppCompatDelegate.setDefaultNightMode(theme2)
            }
            R.id.resetItem -> {
                preferences.edit { clear() }
                ProcessPhoenix.triggerRebirth(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}