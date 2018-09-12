package com.hendraanggrian.errorbar.demo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.hendraanggrian.material.errorbar.Errorbar
import com.hendraanggrian.material.errorbar.addCallback
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity() {

    private lateinit var shortLengthItem: MenuItem
    private lateinit var longLengthItem: MenuItem
    private lateinit var indefiniteLengthItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_demo, menu)
        shortLengthItem = menu.findItem(R.id.shortLengthItem)
        longLengthItem = menu.findItem(R.id.longLengthItem)
        indefiniteLengthItem = menu.findItem(R.id.indefiniteLengthItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.make -> Errorbar.make(frameLayout, "No internet connection", length)
                .setAction("Retry") {
                    Snackbar.make(frameLayout, "Clicked", Snackbar.LENGTH_SHORT).show()
                }
                .addCallback {
                    onShown {
                        Toast.makeText(this@DemoActivity, "shown", Toast.LENGTH_SHORT).show()
                    }
                    onDismissed { _, event ->
                        Toast.makeText(
                            this@DemoActivity,
                            "dismissed event: $event",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .show()
            else -> item.isChecked = true
        }
        return super.onOptionsItemSelected(item)
    }

    private inline val length: Int
        get() = when {
            shortLengthItem.isChecked -> Errorbar.LENGTH_SHORT
            longLengthItem.isChecked -> Errorbar.LENGTH_LONG
            else -> Errorbar.LENGTH_INDEFINITE
        }
}