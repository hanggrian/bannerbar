package com.example.errorbar

import android.os.Bundle
import android.support.design.widget.Errorbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.hendraanggrian.kota.content.res.toPx
import kotlinx.android.synthetic.main.activity_example.*

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class Example2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        setSupportActionBar(toolbar)
        Errorbar.make(frameLayout, "You have no new emails", Errorbar.LENGTH_INDEFINITE)
                .setBackdropResource(R.drawable.bg_empty)
                .setContentMarginBottom(64.toPx())
                .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
