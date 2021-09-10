package com.example.weatherappkotlin.ui.main.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.databinding.DetailsFragmentBinding
import com.example.weatherappkotlin.databinding.MainFragmentBinding
import com.example.weatherappkotlin.ui.main.model.Weather
import com.example.weatherappkotlin.ui.main.viewmodel.MainViewModel
import com.example.weatherappkotlin.ui.main.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar


class DetailsFragment : Fragment() {

    companion object {
        const val WEATHER_PAR = "WEATHER_PAR"
        fun newInstance(bundle: Bundle):DetailsFragment =
            DetailsFragment().apply {arguments = bundle }
        }
    private var _binding: DetailsFragmentBinding? = null
    private val  binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { val view = inflater.inflate(R.layout.details_fragment, container, false)

        _binding = DetailsFragmentBinding.bind(view)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Weather>(WEATHER_PAR)?.let {
            weather -> weather.city.also { city ->

            binding.city.text = city.name
            binding.lat.text = city.lat.toString()
            binding.Long.text = city.long.toString() }

            with(binding) {
            temperature.text = weather.temperature.toString()
            feelsLike.text = weather.feelsLike.toString()
            }
         }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


