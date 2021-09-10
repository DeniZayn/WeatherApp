package com.example.weatherappkotlin.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0,
)  : Parcelable

fun getDefaultCity(): City = City("Moscow",59.56,30.18)

    fun getWorldCities(): List<Weather> = listOf(
    Weather (City("London", 51.51, -0.13), 10, 8 ),
    Weather (City("Atlanta", 33.45, -84.23), 15, 16 ),
    Weather (City("Tokyo", 35.39, 139.50), 13, 10 )
    )

    fun getRussianCities(): List<Weather> = listOf(
        Weather (City("Moscow", 59.56, 30.18), 13, 11 ),
        Weather (City("Saint Petersburg", 59.93, 30.34), 11, 6 ),
        Weather (City("Ufa", 54.74, 139.50), 13, 10 )
    )