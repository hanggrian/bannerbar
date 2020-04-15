package com.example.bannerbar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentTransaction
import com.hendraanggrian.prefy.BindPreference
import com.hendraanggrian.prefy.PreferencesSaver
import com.hendraanggrian.prefy.Prefy
import com.hendraanggrian.prefy.android.bind
import kotlinx.android.synthetic.main.activity_example.*

class ExampleActivity : AppCompatActivity() {

    @JvmField @BindPreference("theme") var theme2 = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    lateinit var saver: PreferencesSaver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.frameLayout, ExampleFragment())
            .commitNow()

        saver = Prefy.bind(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_example, menu)
        menu.findItem(
            when (theme2) {
                AppCompatDelegate.MODE_NIGHT_NO -> R.id.lightItem
                AppCompatDelegate.MODE_NIGHT_YES -> R.id.darkItem
                else -> R.id.defaultItem
            }
        ).isChecked = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        theme2 = when (item.itemId) {
            R.id.lightItem -> AppCompatDelegate.MODE_NIGHT_NO
            R.id.darkItem -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        saver.save()
        AppCompatDelegate.setDefaultNightMode(theme2)
        return super.onOptionsItemSelected(item)
    }
}