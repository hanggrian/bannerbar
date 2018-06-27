package com.example.errorbar

import android.os.Bundle
import android.support.design.widget.Errorbar
import android.support.design.widget.addCallback
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private lateinit var lengthShortItem: MenuItem
    private lateinit var lengthLongItem: MenuItem
    private lateinit var lengthIndefiniteItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        lengthShortItem = menu.findItem(R.id.lengthShortItem)
        lengthLongItem = menu.findItem(R.id.lengthLongItem)
        lengthIndefiniteItem = menu.findItem(R.id.lengthIndefiniteItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.make -> {
                val errorbar = Errorbar.make(frameLayout, "No internet connection", length)
                    .setAction("Retry") {
                        snackbar(frameLayout, "Clicked.")
                    }
                    .addCallback {
                        onShown {
                            toast("shown")
                        }
                        onDismissed { _, event ->
                            toast("dismissed event: $event")
                        }
                    }
                errorbar.show()
            }
            else -> item.isChecked = true
        }
        return super.onOptionsItemSelected(item)
    }

    private inline val length
        get() = when {
            lengthShortItem.isChecked -> Errorbar.LENGTH_SHORT
            lengthLongItem.isChecked -> Errorbar.LENGTH_LONG
            else -> Errorbar.LENGTH_INDEFINITE
        }
}