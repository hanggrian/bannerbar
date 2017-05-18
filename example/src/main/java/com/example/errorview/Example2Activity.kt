package com.example.errorview

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.FrameLayout
import butterknife.BindView
import com.hendraanggrian.widget.ErrorView

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class Example2Activity : BaseActivity() {

    override val contentView: Int
        get() = R.layout.activity_example

    @BindView(R.id.toolbar_example) lateinit var toolbar: Toolbar
    @BindView(R.id.framelayout_example) lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        ErrorView.make(frameLayout!!, "You have no new emails", ErrorView.LENGTH_INDEFINITE)
                .setBackdropDrawable(R.drawable.bg_empty)
                .setLogoDrawable(null)
                .setContentMarginBottom(resources.getDimension(R.dimen.example2_content_margin).toInt())
                .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}
