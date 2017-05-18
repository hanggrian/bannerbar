package com.example.errorview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import butterknife.BindView

class MainActivity : BaseActivity() {

    override val contentView: Int
        get() = R.layout.activity_main

    @BindView(R.id.button_main_example1) lateinit var button1: Button
    @BindView(R.id.button_main_example2) lateinit var button2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button1.setOnClickListener { start(Example1Activity::class.java) }
        button2.setOnClickListener { start(Example2Activity::class.java) }
    }

    private fun start(cls: Class<out Activity>) {
        startActivity(Intent(this, cls))
    }
}