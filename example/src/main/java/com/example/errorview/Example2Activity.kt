package com.example.errorview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.hendraanggrian.kota.content.toPx
import com.hendraanggrian.widget.ErrorView2
import com.hendraanggrian.widget.errorView
import kotlinx.android.synthetic.main.activity_example.*

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class Example2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        setSupportActionBar(toolbar)
        frameLayout.errorView("You have no new emails", ErrorView2.LENGTH_INDEFINITE)
                .setBackdropDrawable(R.drawable.bg_empty)
                .setLogoDrawable(null)
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
