package com.example.weatherappkotlin.ui.main.model

import retrofit2.Callback

//import okhttp3.Callback


class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository{

    override fun getWeatherDetailFromServer(lat: Double, lon: Double, callback: Callback<WeatherDTO>){
        remoteDataSource.getWeatherDetails(lat, lon, callback)
    }
}