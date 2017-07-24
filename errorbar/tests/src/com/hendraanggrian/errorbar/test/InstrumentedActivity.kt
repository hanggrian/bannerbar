package com.hendraanggrian.errorbar.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout
import android.widget.ProgressBar

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class InstrumentedActivity : AppCompatActivity() {

    var progressBar: ProgressBar? = null
    var frameLayout: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrumented)
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        progressBar = findViewById(R.id.progressBar) as ProgressBar
        frameLayout = findViewById(R.id.frameLayout) as FrameLayout
    }
}