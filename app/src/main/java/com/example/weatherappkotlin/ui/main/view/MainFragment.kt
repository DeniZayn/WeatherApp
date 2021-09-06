package com.example.weatherappkotlin.ui.main.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.databinding.MainFragmentBinding
import com.example.weatherappkotlin.ui.main.model.Weather
import com.example.weatherappkotlin.ui.main.viewmodel.MainViewModel
import com.example.weatherappkotlin.ui.main.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var viewModel: MainViewModel

    private var _binding: MainFragmentBinding? = null // binding
    private val  binding get() = _binding!!           // binding
    private lateinit var adapter: MainAdapter

  //  private var isRus : Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View { val view = inflater.inflate(R.layout.main_fragment, container, false) // binding

        _binding = MainFragmentBinding.bind(view) // binding
        return binding.root                       // binding
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MainAdapter()
        adapter.listener =
            MainAdapter.OnItemViewClickListener { weather ->
                val manager = activity?.supportFragmentManager
                if (manager != null) {
                    val bundle = Bundle()
                    bundle.putParcelable(DetailsFragment.WEATHER_PAR, weather)
                    manager.beginTransaction()
                        .replace(R.id.container, DetailsFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commit()
                }
            }
        binding.recyclerview.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener{
        }

 //       viewModel.onLanguageChange()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.liveData.observe(viewLifecycleOwner, { state ->
             renderData(state)
        })

        viewModel.liveDataIsRus.observe(viewLifecycleOwner, { isRus ->
                if (isRus) {
                    binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
                }else {
                    binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
                }
            viewModel.getWeatherFromLocalSource()
            })
        }

    private fun renderData(state: AppState) {

        when(state) {
          is AppState.Loading -> binding.loadingLayout.visibility= View.VISIBLE
          is AppState.Success -> {
              binding.loadingLayout.visibility = View.GONE
              adapter.weatherData = state.weather
//              binding.loadingLayout.visibility = View.GONE
//              binding.message.text = "${state.weather.city.name}"  +
//                      "\n lat/long ${state.weather.city.lat} ${state.weather.city.long}" +
//                      "\n temperature ${state.weather.temperature}" +
//                      "\n feels like ${state.weather.feelsLike}"
          }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.mainFragmentFAB, "Error:${state.error}", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getWeatherFromLocalSource() }
                    .show()
            }

        }
 //       binding.message.text = data                         // binding
 //       binding.TestButton2. setOnClickListener {
 //         viewModel.liveData                                // setOnClickListener
 //       }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


