package com.example.weatherappkotlin.ui.main.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter: MainAdapter by lazy { MainAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false) // binding
        _binding = MainFragmentBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.listener = MainAdapter.OnItemViewClickListener { weather ->
            activity?.supportFragmentManager?.let { fragmentManager ->
                fragmentManager.beginTransaction()
                    .replace(
                        R.id.container, DetailsFragment.newInstance(Bundle().apply
                        {
                            putParcelable(DetailsFragment.FACT_WEATHER_EXTRA, weather)
                        })
                    )
                    .addToBackStack("")
                    .commit()
            }
        }
        binding.recyclerview.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener {
        }
        viewModel
        viewModel.liveData.observe(viewLifecycleOwner, { state ->
            renderData(state)
        })

        viewModel.liveDataIsRus.observe(viewLifecycleOwner, { isRus ->
            if (isRus) {
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            } else {
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
            }
            viewModel.getWeatherFromLocalSource()
        })
    }

    private fun renderData(state: AppState) {

        when (state) {
            is AppState.Loading ->
                binding.loadingLayout.show()
            is AppState.Success -> {
                binding.loadingLayout.hide()
                adapter.weatherData = state.weather
            }
            is AppState.Error -> {
                binding.loadingLayout.hide()
                binding.mainFragmentFAB.showSnackBar(
                    "Error:${state.error}",
                    "Reload",
                    { viewModel.getWeatherFromLocalSource() }
                )
            }
        }
    }
        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null

    }
}


