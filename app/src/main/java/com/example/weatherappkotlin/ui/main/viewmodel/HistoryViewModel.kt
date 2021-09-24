package com.example.weatherappkotlin.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherappkotlin.ui.main.model.LocalRepositoryImpl
import com.example.weatherappkotlin.ui.main.model.database.HistoryEntity
import com.example.weatherappkotlin.ui.main.view.App

class HistoryViewModel : ViewModel() {

    private val historyRepository = LocalRepositoryImpl(App.getHistoryDao())

    fun getAllHistory(): List<HistoryEntity> = historyRepository.getAllHistory()
}