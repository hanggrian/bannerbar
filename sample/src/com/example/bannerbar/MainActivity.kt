package com.example.bannerbar

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Bannerbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.addCallback
import com.hendraanggrian.prefs.BindPreference
import com.hendraanggrian.prefs.PreferencesSaver
import com.hendraanggrian.prefs.android.bindPreferences
import com.hendraanggrian.prefs.android.preferences
import com.jakewharton.processphoenix.ProcessPhoenix
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnLongClickListener {
    @BindPreference("theme") @JvmField var theme2 = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    @BindPreference lateinit var duration: String
    @BindPreference @JvmField var showIcon = false
    @BindPreference @JvmField var showSubtitle = false
    @BindPreference @JvmField var actionCount = 0
    @BindPreference lateinit var animationMode: String
    @BindPreference("titleColor") @JvmField @ColorInt var titleColor2 = Color.TRANSPARENT
    @BindPreference @JvmField @ColorInt var subtitleColor = Color.TRANSPARENT
    @BindPreference @JvmField @ColorInt var actionTextColors = Color.TRANSPARENT
    @BindPreference @JvmField @ColorInt var backgroundTint = Color.TRANSPARENT

    private lateinit var saver: PreferencesSaver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.frameLayout, MainFragment())
            .commitNow()
        saver = bindPreferences()
        fab.setOnLongClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
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

    fun show(view: View) {
        saver = bindPreferences()
        val bannerbar = Bannerbar.make(fab, TITLE, duration.toInt())
        if (showIcon) bannerbar.setIcon(R.drawable.ic_error)
        if (showSubtitle) bannerbar.setSubtitle(SUBTITLE)
        repeat(actionCount) {
            bannerbar.addAction(if (it == 0) ACTION_TEXT1 else ACTION_TEXT2)
        }
        bannerbar.animationMode = animationMode.toInt()
        titleColor2.ifConfigured { bannerbar.setTitleColor(it) }
        subtitleColor.ifConfigured { bannerbar.setSubtitleColor(it) }
        actionTextColors.ifConfigured { bannerbar.setActionsTextColor(it) }
        backgroundTint.ifConfigured { bannerbar.setBackgroundTint(it) }
        bannerbar.addCallback {
            onShown { Log.d("Bannerbar", "Shown") }
            onDismissed { _, event -> Log.d("Bannerbar", "Dismissed event: $event") }
        }
        bannerbar.show()
    }

    companion object {
        const val TITLE = "Mobile data is off"
        const val SUBTITLE = "No data connection. Consider turning on mobile data or turning on Wi-Fi."
        const val ACTION_TEXT1 = "Dismiss"
        const val ACTION_TEXT2 = "Turn on WiFi"

        private fun @receiver:ColorInt Int.ifConfigured(action: (Int) -> Unit) {
            if (this != Color.TRANSPARENT) action(this)
        }
    }

    override fun onLongClick(view: View): Boolean {
        saver = bindPreferences()
        val snackbar = Snackbar.make(fab, TITLE, duration.toInt())
        snackbar.setAction(
            when (actionCount) {
                1 -> ACTION_TEXT1
                2 -> ACTION_TEXT2
                else -> null
            }
        ) { }
        snackbar.animationMode = animationMode.toInt()
        titleColor2.ifConfigured { snackbar.setTextColor(it) }
        actionTextColors.ifConfigured { snackbar.setActionTextColor(it) }
        backgroundTint.ifConfigured { snackbar.setBackgroundTint(it) }
        snackbar.show()
        return false
    }
}