package com.hendraanggrian.material.backdrop

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.hendraanggrian.material.backdrop.test.R

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