package com.example.weatherappkotlin.ui.main.model

import com.example.weatherappkotlin.ui.main.model.database.HistoryDao
import com.example.weatherappkotlin.ui.main.model.database.HistoryEntity

interface  LocalRepository {

    fun getAllHistory(): List<HistoryEntity>
    fun saveEntity(weather: HistoryEntity)
}