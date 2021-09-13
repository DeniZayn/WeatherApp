package com.example.weatherappkotlin.ui.main.view

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.databinding.DetailsFragmentBinding
import com.example.weatherappkotlin.databinding.MainFragmentBinding
import com.example.weatherappkotlin.ui.main.model.City
import com.example.weatherappkotlin.ui.main.model.Weather
import com.example.weatherappkotlin.ui.main.model.WeatherDTO
import com.example.weatherappkotlin.ui.main.viewmodel.MainViewModel
import com.example.weatherappkotlin.ui.main.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


class DetailsFragment : Fragment() {

    companion object {
        const val WEATHER_PAR = "WEATHER_PAR"
        fun newInstance(bundle: Bundle): DetailsFragment =
            DetailsFragment().apply { arguments = bundle }
    }

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.details_fragment, container, false)

        _binding = DetailsFragmentBinding.bind(view)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Weather>(WEATHER_PAR)?.let { weather ->
            weather.city.also { city ->

                binding.city.text = city.name
                binding.cityCoordinate.text = "${city.lat} -${city.long}"
            }
            Thread(){
                goToInternet(
                    uri = URL("https://api.weather.yandex.ru/v1/forecast?lat=${weather.city.lat}&lon=${weather.city.long}&lang=ru_RU"))
            }.start()
        }
    }
    fun displayWeather(weather: WeatherDTO) {

            with(binding) {
                temperatureValue.text = weather.fact?.temp.toString()
                feelsLikeValue.text = weather.fact?.feels_like.toString()
                weatherCondition.text = weather.fact?.condition.toString()
            }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun goToInternet(uri: URL) {
        var urlConnection: HttpsURLConnection? = null
        try {
            urlConnection = uri.openConnection() as HttpsURLConnection
            urlConnection.apply {
                requestMethod = "GET"
                readTimeout = 10000
                addRequestProperty("X-Yandex-API-Key", "662d9416-8464-4808-9223-ebc72a8a203b")
            }
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val result = reader.lines().collect(Collectors.joining("\n"))

            val weatherDTO: WeatherDTO = Gson().fromJson(result, WeatherDTO::class.java)
            requireActivity().runOnUiThread{
 //               binding.webview.loadDetailWithBaseURL(null, result, "text/html; charset = utf-8", "utf-8", null )
                displayWeather (weatherDTO)
            }
        } catch (e: Exception) {
            Log.e("", "FAILED",e)
        } finally {
            urlConnection?.disconnect()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
