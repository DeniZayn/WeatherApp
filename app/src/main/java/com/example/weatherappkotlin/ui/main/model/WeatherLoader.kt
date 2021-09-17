package com.example.weatherappkotlin.ui.main.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WeatherLoader (
    private val lat: Double,
    private val  lon: Double,
    private val listener: WeatherLoaderListener

    ){

    @RequiresApi(Build.VERSION_CODES.N)
    fun goToInternet() {

        Thread {
 //           val uri = URL("https://api.weather.yandex.ru/v1/forecast?lat=${lat}&lon=${lon}&lang=ru_RU")

 //           var urlConnection: HttpsURLConnection? = null

 //           try {
 //               urlConnection = uri.openConnection() as HttpsURLConnection
 //               urlConnection.apply {
 //                   requestMethod = "GET"
 //                   readTimeout = 10000
 //                   addRequestProperty("X-Yandex-API-Key", "662d9416-8464-4808-9223-ebc72a8a203b")
 //               }
 //               val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
 //               val result = reader.lines().collect(Collectors.joining("\n"))

 //               val weatherDTO: WeatherDTO = Gson().fromJson(result, WeatherDTO::class.java)
 //               listener.onLoaded(weatherDTO)

 //           } catch (e: Exception) {
 //               listener.onFailed(e)
 //               Log.e("", "FAILED",e)
 //           } finally {
 //               urlConnection?.disconnect()
 //           }

        }.start()
    }

    interface WeatherLoaderListener {
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(throwable: Throwable)
    }
}


