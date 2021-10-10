package com.example.flavortestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = BuildConfig.TYPE

            //       findViewById<TextView>(R.id.textView).text = BuildConfig.HOST

    }
}