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
import coil.api.load
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.databinding.DetailsFragmentBinding
import com.example.weatherappkotlin.databinding.MainFragmentBinding
import com.example.weatherappkotlin.ui.main.model.*
import com.example.weatherappkotlin.ui.main.viewmodel.MainViewModel
import com.example.weatherappkotlin.ui.main.viewmodel.AppState
import com.example.weatherappkotlin.ui.main.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.android.synthetic.main.main_fragment_item.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URI
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection
import okhttp3.Callback as Callback1


class DetailsFragment : Fragment() {

    companion object {
        const val WEATHER_PAR = "WEATHER_PAR"
        fun newInstance(bundle: Bundle): DetailsFragment =
            DetailsFragment().apply { arguments = bundle }
    }

    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
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

        val weather = arguments?.getParcelable(FACT_WEATHER_EXTRA)?:  Weather ()

        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            renderData(state)
        }
        viewModel.getWeatherFromRemoteSource(weather)
    }

    private fun renderData(state: AppState) {
        when (state) {
            is AppState.Loading -> {
                binding.mainView.hide()
                binding.loadingLayout.show()
            }
            is AppState.Success -> {
                binding.mainView.show()
                binding.loadingLayout.hide()
                val weather = state.weather.first()

                with(binding) {
                    binding.imageView.load ("https://freepngimg.com/thumb/city/36275-3-city-hd.png")

                    temperatureValue.text = weather.temperature.toString()
                    feelsLikeValue.text = weather.feelsLike.toString()
                    weatherCondition.text = weather.condition

                    city.text = weather.city.name
                    cityCoordinate.text = "${weather.city.lat} ${weather.city.lon}"
                }
            }
            is AppState.Error -> {
                binding.loadingLayout.show()

                binding.mainView.showSnackBar(
                    "Error:${state.error}",
                    "Reload",
                    { viewModel.getWeatherFromRemoteSource(Weather()) }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
