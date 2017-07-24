package com.example.errorbar

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonExample1.setOnClickListener { startActivity(Intent(this, Example1Activity::class.java)) }
        buttonExample2.setOnClickListener { startActivity(Intent(this, Example2Activity::class.java)) }
    }
}