package com.example.weatherappkotlin.ui.main.model

data class Weather (
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0,
)
    fun getDefaultCity(): City = City("Moscow",59.56,30.18)