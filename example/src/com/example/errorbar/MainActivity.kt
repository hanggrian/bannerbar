package com.example.errorbar

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun example1(v: View) = startActivity(Intent(this, Example1Activity::class.java))

    fun example2(v: View) = startActivity(Intent(this, Example2Activity::class.java))
}