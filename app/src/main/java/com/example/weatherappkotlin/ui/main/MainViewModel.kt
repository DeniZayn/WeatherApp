package com.example.app_weather_mvvm.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val liveDataToObserve: MutableLiveData<String> = MutableLiveData()

    fun getData(): LiveData<String>{

        getDataFromLocalService()

        return liveDataToObserve
    }
    private fun getDataFromLocalService() {
        Thread {
            Thread.sleep(5000)
            liveDataToObserve.postValue("Новые Данные")
            Thread.sleep(5000)
            liveDataToObserve.postValue("Еще Новее Данные")
            Thread.sleep(5000)
            liveDataToObserve.postValue("Самые Новые Данные")
        }.start()
    }

}