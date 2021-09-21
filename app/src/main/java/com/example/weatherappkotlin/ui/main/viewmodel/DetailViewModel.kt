package com.example.weatherappkotlin.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherappkotlin.ui.main.model.*
import com.google.gson.Gson
//import okhttp3.Call
//import okhttp3.Callback
//import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.ParseException


const val MAIN_LINK = "https://api.weather.yandex.ru/v1/forecast?"

class DetailViewModel : ViewModel () {

    private val repository : DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())
    private val detailLiveData = MutableLiveData<AppState>()

    val liveData: LiveData<AppState> = detailLiveData

    fun getWeatherFromRemoteSource(weather: Weather) {
        detailLiveData.value = AppState.Loading

        repository.getWeatherDetailFromServer(
 //           MAIN_LINK + "lat=${weather.city.lat}&lon=${weather.city.lon}",
            weather.city.lat,
            weather.city.lon,
            object : Callback<WeatherDTO> {
 //           override fun onFailure(call: Call, e: IOException) {}



                override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                    detailLiveData.postValue(AppState.Error(t))
                }

                override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                    response.body()?.let {
                        detailLiveData.postValue(checkResponse(it))
                    }
                }
            })
    }
    private fun checkResponse(response: WeatherDTO): AppState{
   //     val weatherDTO = Gson().fromJson(response, WeatherDTO::class.java)
   //     val factDTO = weatherDTO.fact

        val factDTO = response.fact

        return if (factDTO?.condition != null
            && factDTO.condition.isNotBlank()
            && factDTO.temp != null
            && factDTO.feels_like != null
        ) {
            AppState.Success(
                listOf(
                    Weather(
                temperature = factDTO.temp,
                feelsLike = factDTO.feels_like,
                condition = factDTO.condition
            )))
        } else {
            AppState.Error(ParseException("Didn't recognize JSON", 0))
        }
    }
}
