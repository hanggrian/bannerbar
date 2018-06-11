package com.example.errorbar

import android.os.Bundle
import android.support.design.widget.indefiniteErrorbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_example.*

class Example2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        setSupportActionBar(toolbar)
        frameLayout.indefiniteErrorbar {
            text = "You have no new emails"
            backdropResource = R.drawable.bg_empty
            contentMarginBottom = resources.getDimensionPixelSize(R.dimen.margin_bottom)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}