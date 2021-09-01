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

    fun  getWeatherFromLocalSource() = getDataFromLocalSource()
    fun  getWeatherFromRemoteSource() = getWeatherFromLocalSource()


    private fun getDataFromLocalSource() {
     liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(5000)
            if (Random.nextBoolean()) {
                liveDataToObserve.postValue(AppState.Success(repository.getWeatherFromLocalStorage()))
            } else  {liveDataToObserve.postValue(AppState.Error(Exception("No Connection")))}
        }.start()
    }

}
