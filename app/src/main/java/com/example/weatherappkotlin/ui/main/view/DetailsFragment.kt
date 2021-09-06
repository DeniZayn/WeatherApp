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
        fun newInstance(bundle: Bundle):DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment

        }
        const val WEATHER_PAR = "WEATHER_PAR"
    }
 //   private lateinit var viewModel: MainViewModel
    private var _binding: DetailsFragmentBinding? = null // binding
    private val  binding get() = _binding!!           // binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { val view = inflater.inflate(R.layout.details_fragment, container, false) // binding

        _binding = DetailsFragmentBinding.bind(view) // binding
        return binding.root                       // binding
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather = arguments?.getParcelable<Weather>(WEATHER_PAR)
        if (weather != null) {
            binding.message.text = "${weather.city.name}"  +
                    "\n lat/long ${weather.city.lat} ${weather.city.long}" +
                    "\n temperature ${weather.temperature}" +
                    "\n feels like ${weather.feelsLike}"

        }
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        viewModel.liveData.observe(viewLifecycleOwner, { state ->
//             renderData(state)
//        })
    //    viewModel.getWeatherFromLocalSource(isRus)

    }

 //   private fun renderData(state: AppState) {
 //       when(state) {
 //         is AppState.Loading -> binding.loadingLayout.visibility= View.VISIBLE
 //         is AppState.Success -> {
 //             binding.loadingLayout.visibility = View.GONE           }
 //           is AppState.Error -> {
 //               binding.loadingLayout.visibility = View.GONE
 //                   Snackbar
 //                  .make(binding.mainFragmentFAB, "Error:${state.error}", Snackbar.LENGTH_INDEFINITE)
 //                  .setAction("Reload") { viewModel.getWeatherFromLocalSource() }
 //                  .show()             }        }
 //       binding.message.text = data                         // binding
 //       binding.TestButton2. setOnClickListener {
 //         viewModel.liveData                                // setOnClickListener
 //       }     }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


