package com.example.errorbar

import android.os.Bundle
import android.support.design.widget.Errorbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_example.*
import org.jetbrains.anko.toast

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
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
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.item_example1_make -> Errorbar.make(frameLayout, "No internet connection", length)
                    .setBackdropResource(R.drawable.errorbar_bg_cloud)
                    .setLogoResource(R.drawable.errorbar_ic_cloud)
                    .setAction("Retry", {
                        toast("Clicked.")
                    })
                    .addCallback(object : Errorbar.Callback() {
                        override fun onShown(v: Errorbar) = toast("onShown")
                        override fun onDismissed(v: Errorbar, event: Int) = toast("onDismissed event: $event")
                    })
                    .show()
            else -> item.isChecked = true
        }
        return super.onOptionsItemSelected(item)
    }

    private val length get() = if (menu.findItem(R.id.item_example1_length_short).isChecked) {
        Errorbar.LENGTH_SHORT
    } else if (menu.findItem(R.id.item_example1_length_long).isChecked) {
        Errorbar.LENGTH_LONG
    } else {
        Errorbar.LENGTH_INDEFINITE
    }
}