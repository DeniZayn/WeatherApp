package com.example.weatherappkotlin.ui.main.model

//import okhttp3.Callback

import retrofit2.Callback



interface DetailsRepository {

    fun getWeatherDetailFromServer(lat: Double, lon: Double, callback: Callback<WeatherDTO>)

}