package com.example.errorbar

import android.os.Bundle
import android.support.design.widget.Errorbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.core.widget.toast
import kotlinx.android.synthetic.main.activity_example.*

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
            R.id.make -> Errorbar.make(frameLayout, length)
                .setText("No internet connection")
                .setImage(R.drawable.errorbar_ic_cloud)
                .setBackdrop(R.drawable.errorbar_bg_cloud)
                .setAction("Retry") {
                    toast("Clicked.")
                }
                .addCallback(object : Errorbar.Callback() {
                    override fun onShown(v: Errorbar) {
                        toast("onShown")
                    }

                    override fun onDismissed(v: Errorbar, event: Int) {
                        toast("onDismissed event: $event")
                    }
                })
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