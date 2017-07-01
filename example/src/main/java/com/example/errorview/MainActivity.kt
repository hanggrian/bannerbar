package com.example.errorview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonExample1.setOnClickListener { start(Example1Activity::class.java) }
        buttonExample2.setOnClickListener { start(Example2Activity::class.java) }
    }

    private fun start(cls: Class<out Activity>) {
        startActivity(Intent(this, cls))
    }
}