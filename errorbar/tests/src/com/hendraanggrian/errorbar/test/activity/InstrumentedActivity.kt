package com.hendraanggrian.errorbar.test.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.hendraanggrian.errorbar.test.R

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
open class InstrumentedActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrumented)
        setSupportActionBar(findViewById(R.id.toolbar))
        progressBar = findViewById(R.id.progressBar)
        frameLayout = findViewById(R.id.frameLayout)
    }
}