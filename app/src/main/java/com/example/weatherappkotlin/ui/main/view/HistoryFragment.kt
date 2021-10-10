package com.example.weatherappkotlin.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.databinding.DetailsFragmentBinding
import com.example.weatherappkotlin.databinding.FragmentHistoryBinding
import com.example.weatherappkotlin.ui.main.viewmodel.DetailViewModel
import com.example.weatherappkotlin.ui.main.viewmodel.HistoryAdapter
import com.example.weatherappkotlin.ui.main.viewmodel.HistoryViewModel


class HistoryFragment : Fragment() {


    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        _binding = FragmentHistoryBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = adapter
        adapter.setData(viewModel.getAllHistory())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}