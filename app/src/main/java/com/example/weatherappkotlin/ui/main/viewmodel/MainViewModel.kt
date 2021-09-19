package com.example.weatherappkotlin.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherappkotlin.ui.main.model.Repository
import com.example.weatherappkotlin.ui.main.model.RepositoryImpl
import java.lang.Exception
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private var liveDataIsRusToObserve : MutableLiveData<Boolean> = MutableLiveData(true)
    private val repository: Repository = RepositoryImpl()

    val liveData: LiveData<AppState> = liveDataToObserve
    val liveDataIsRus: LiveData<Boolean> = liveDataIsRusToObserve

    fun getWeatherFromLocalSource() = getDataFromLocalSource()
    fun onLanguageChange(){
        liveDataIsRusToObserve.value = liveDataIsRusToObserve.value == false
    }

    private fun getDataFromLocalSource(isRussian: Boolean = true) {
     liveDataToObserve.value = AppState.Loading
             liveDataToObserve.postValue(
                 AppState.Success(
                 if  (liveDataIsRusToObserve.value == true) {
                     repository.getWeatherFromLocalStorageRus()
                 } else { repository.getWeatherFromLocalStorageWorld()
                 }
             )
         )
     }
}
