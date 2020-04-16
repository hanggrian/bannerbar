package com.hendraanggrian.material.bannerbar.test

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

open class TestActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar
    lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        setSupportActionBar(findViewById(R.id.toolbar))
        progressBar = findViewById(R.id.progressBar)
        frameLayout = findViewById(R.id.frameLayout)
    }
}