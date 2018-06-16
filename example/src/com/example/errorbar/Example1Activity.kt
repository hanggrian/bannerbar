package com.example.errorbar

import android.os.Bundle
import android.support.design.widget.Errorbar
import android.support.design.widget.addCallback
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_example.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast

class Example1Activity : AppCompatActivity() {

    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.example1, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.make -> Errorbar.make(frameLayout, "No internet connection", length)
                .setBackground(R.drawable.errorbar_bg_cloud)
                .setIcon(R.drawable.errorbar_ic_cloud)
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
                .show()
            else -> item.isChecked = true
        }
        return super.onOptionsItemSelected(item)
    }

    private val length
        get() = when {
            menu.findItem(R.id.length_short).isChecked -> Errorbar.LENGTH_SHORT
            menu.findItem(R.id.length_long).isChecked -> Errorbar.LENGTH_LONG
            else -> Errorbar.LENGTH_INDEFINITE
        }
}