package com.example.errorview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.hendraanggrian.support.utils.content.Resources2
import com.hendraanggrian.widget.ErrorView
import kotlinx.android.synthetic.main.activity_example.*

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class Example2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        setSupportActionBar(toolbar)
        ErrorView.make(frameLayout, "You have no new emails", ErrorView.LENGTH_INDEFINITE)
                .setBackdropDrawable(R.drawable.bg_empty)
                .setLogoDrawable(null)
                .setContentMarginBottom(Resources2.toPx(64))
                .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}
