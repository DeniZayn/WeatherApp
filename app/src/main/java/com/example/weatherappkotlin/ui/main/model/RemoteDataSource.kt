package com.example.weatherappkotlin.ui.main.model

import com.example.weatherappkotlin.ui.main.view.hide
import com.example.weatherappkotlin.ui.main.view.show
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Callback
//import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


const val API_KEY = "662d9416-8464-4808-9223-ebc72a8a203b"

class RemoteDataSource {

    private val weatherApi = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru/")
        .addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create()
            )
        )
        .build()
        .create(WeatherAPI::class.java)


    fun getWeatherDetails(lat: Double, lon: Double, callback: Callback<WeatherDTO>) {

//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url(link)
//            .addHeader("X-Yandex-API-Key", API_KEY )
//            .get()
//            .build()
//        client.newCall(request).enqueue(callback)

        weatherApi.getWeather(API_KEY,lat, lon).enqueue(callback)
    }
}