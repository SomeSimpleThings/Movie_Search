package com.geekbrains.moviesearch.ui.search

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.geekbrains.moviesearch.R

import com.geekbrains.moviesearch.ui.search.dummy.DummyContent.DummyItem


class MysearchRecyclerViewAdapter(
    private val values: List<DummyItem>
) : RecyclerView.Adapter<MysearchRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.movie_name)
        val contentView: TextView = view.findViewById(R.id.movie_year)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}