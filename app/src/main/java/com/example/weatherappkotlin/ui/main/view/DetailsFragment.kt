package com.example.weatherappkotlin.ui.main.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.databinding.DetailsFragmentBinding
import com.example.weatherappkotlin.databinding.MainFragmentBinding
import com.example.weatherappkotlin.ui.main.model.*
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

    private val localResultBroadcastReceiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.getStringExtra(RESULT_EXTRA)){
                SUCCESS_RESULT -> {
                    intent.getParcelableExtra<WeatherDTO.FactDTO>(FACT_WEATHER_EXTRA)?.let {
                        displayWeather(it)
                    }
                }
 //               ERROR_EMPTY_DATA_RESULT -> {
                  else -> {
                      binding.mainView.showSnackBar("Error", "Pls try again", {view ->
                      })
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(localResultBroadcastReceiver, IntentFilter(DETAILS_INTENT_FILTER))
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(localResultBroadcastReceiver)
        super.onDestroy()
    }

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

                getWeather(city.lat, city.long)
            }
        }
    }
    private fun getWeather(lat: Double, long: Double) {
        binding.mainView.hide()
        binding.loadingLayout.show()

        requireActivity().startService(
            Intent(requireContext(), MainService::class.java).apply {
                putExtra(LATITUDE_EXTRA, lat )
                putExtra(LONGITUDE_EXTRA, long )
            }
        )
    }

    fun displayWeather(fact: WeatherDTO.FactDTO) {
        binding.mainView.show()
        binding.loadingLayout.hide()

            with(binding) {
                temperatureValue.text = fact.temp.toString()
                feelsLikeValue.text = fact.feels_like.toString()
                weatherCondition.text = fact.condition.toString()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
