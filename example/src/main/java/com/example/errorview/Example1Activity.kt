package com.example.errorview

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import butterknife.BindView
import com.hendraanggrian.widget.ErrorView

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class Example1Activity : BaseActivity() {

    override val contentView: Int
        get() = R.layout.activity_example

    @BindView(R.id.toolbar_example) lateinit var toolbar: Toolbar
    @BindView(R.id.framelayout_example) lateinit var frameLayout: FrameLayout
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            R.id.item_example1_make -> ErrorView.make(frameLayout!!, "No internet connection", length)
                    .setAction("Retry", null)
                    .setOnDismissListener { _, event ->
                        Toast.makeText(this@Example1Activity, ("dismiss event: " + event).toString(), Toast.LENGTH_SHORT)
                                .show()
                    }
                    .show()
            else -> item.isChecked = true
        }
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

    private val length: Int
        @ErrorView.Duration
        get() {
            if (menu!!.findItem(R.id.item_example1_length_short).isChecked)
                return ErrorView.LENGTH_SHORT
            else if (menu!!.findItem(R.id.item_example1_length_long).isChecked)
                return ErrorView.LENGTH_LONG
            else
                return ErrorView.LENGTH_INDEFINITE
        }
}