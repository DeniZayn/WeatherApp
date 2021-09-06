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
    private var liveDataIsRusToObserve : MutableLiveData<Boolean> = MutableLiveData(true)
    private val repository: Repository = RepositoryImpl()


    val liveData: LiveData<AppState> = liveDataToObserve                          // AppState
    val liveDataIsRus: LiveData<Boolean> = liveDataIsRusToObserve

    fun getWeatherFromLocalSource() = getDataFromLocalSource()

    fun onLanguageChange(){
        liveDataIsRusToObserve.value = liveDataIsRusToObserve.value == false
    }

    private fun getDataFromLocalSource(isRussian: Boolean = true) {
     liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(2000)
             liveDataToObserve.postValue(
                 AppState.Success(
                 if  (liveDataIsRusToObserve.value == true) {
                     repository.getWeatherFromLocalStorageRus()
                 } else { repository.getWeatherFromLocalStorageWorld()
                 }
             )
             )
        }.start()
    }
}
