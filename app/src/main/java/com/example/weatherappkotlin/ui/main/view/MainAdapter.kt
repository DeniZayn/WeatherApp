package com.example.weatherappkotlin.ui.main.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.weatherappkotlin.ui.main.model.Weather

class MainAdapter: RecyclerView.Adapter <MainAdapter.MainViewHolder> (){

    var weatherData: List<Weather> = listOf()

    inner class MainViewHolder(view: View): ViewHolder(view) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}