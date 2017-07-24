package com.example.errorview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.hendraanggrian.widget.ErrorView2
import kotlinx.android.synthetic.main.activity_example.*
import org.jetbrains.anko.toast

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class Example1Activity : AppCompatActivity() {

    private var menu: Menu? = null

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
            R.id.item_example1_make -> ErrorView2.make(frameLayout, "No internet connection", length)
                    .setBackdropDrawable(R.drawable.errorview_bg_cloud)
                    .setLogoDrawable(R.drawable.errorview_ic_cloud)
                    .setAction("Retry", {
                        toast("Clicked.")
                        true
                    })
                    .setOnDismissListener { _, event -> toast("internalDismiss event: " + event) }
                    .show()
            else -> item.isChecked = true
        }
        return super.onOptionsItemSelected(item)
    }

    private val length: Int @ErrorView2.Companion.Duration get() {
        if (menu!!.findItem(R.id.item_example1_length_short).isChecked)
            return ErrorView2.LENGTH_SHORT
        else if (menu!!.findItem(R.id.item_example1_length_long).isChecked)
            return ErrorView2.LENGTH_LONG
        else
            return ErrorView2.LENGTH_INDEFINITE
    }
}