package com.example.weatherappkotlin.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {

    private val binding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}