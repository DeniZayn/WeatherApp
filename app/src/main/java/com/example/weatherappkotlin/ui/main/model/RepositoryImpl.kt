package com.example.weatherappkotlin.ui.main.model

import com.example.weatherappkotlin.ui.main.model.Repository

class RepositoryImpl: Repository {
    override fun getWeatherFromServer(): Weather = Weather()

    override fun getWeatherFromLocalStorageRus(): List<Weather> = getRussianCities()

    override fun getWeatherFromLocalStorageWorld(): List<Weather> = getWorldCities()


}