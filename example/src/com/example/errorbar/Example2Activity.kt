package com.example.errorbar

import android.os.Bundle
import android.support.design.widget.Errorbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.errorbar.R.id.frameLayout
import com.example.errorbar.R.id.toolbar
import kotlinx.android.synthetic.main.activity_example.*

class Example2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        setSupportActionBar(toolbar)
        Errorbar.make(frameLayout, Errorbar.LENGTH_INDEFINITE)
            .setText("You have no new emails")
            .setBackdrop(R.drawable.bg_empty)
            .setContentMarginBottom(resources.getDimensionPixelSize(R.dimen.margin_bottom))
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}