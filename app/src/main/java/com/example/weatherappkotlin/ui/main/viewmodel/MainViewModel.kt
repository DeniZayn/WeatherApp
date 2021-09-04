package com.example.weatherappkotlin.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherappkotlin.ui.main.model.Repository
import com.example.weatherappkotlin.ui.main.model.RepositoryImpl
import java.lang.Exception
import kotlin.random.Random

class MainViewModel : ViewModel() {


    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()   // AppState
    private val repository: Repository = RepositoryImpl()

    val liveData: LiveData<AppState> = liveDataToObserve                          // AppState


    private fun getDataFromLocalSource(isRussian: Boolean = true) {
     liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(2000)
             liveDataToObserve.postValue(
                 if (isRussian) {
                     AppState.Success(repository.getWeatherFromLocalStorageRus())
                 } else {AppState.Success(repository.getWeatherFromLocalStorageWorld())

                 }
             )
        }.start()
    }

}
