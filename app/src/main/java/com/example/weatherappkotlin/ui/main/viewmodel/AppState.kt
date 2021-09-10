package com.example.weatherappkotlin.ui.main.viewmodel

import com.example.weatherappkotlin.ui.main.model.Weather

sealed class AppState {

    data class Success(val weather: List<Weather>): AppState()
    data class Error(val error: Throwable): AppState()
    object Loading : AppState()
}