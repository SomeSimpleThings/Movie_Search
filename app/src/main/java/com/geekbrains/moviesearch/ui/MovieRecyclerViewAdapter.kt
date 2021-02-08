package com.geekbrains.moviesearch.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.geekbrains.moviesearch.R

import com.geekbrains.moviesearch.ui.DummyContent.DummyItem

class MovieRecyclerViewAdapter(
    private val values: List<DummyItem>,
    val layoutId: Int
) : RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.yearView.text = "2020"
        holder.nameView.text = item.content
        holder.rateView.text = "8.2"
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val yearView: TextView = view.findViewById(R.id.movie_year)
        val nameView: TextView = view.findViewById(R.id.movie_name)
        val rateView: TextView = view.findViewById(R.id.movie_rate)

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }
}