package com.geekbrains.moviesearch.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.ui.MoviesAdapter
import com.geekbrains.moviesearch.ui.OnMovieItemClickListener
import com.geekbrains.moviesearch.vo.Category

class CategoryAdapter(
    val categoryClickListener: OnCategoryClickListener,
    val clickListener: OnMovieItemClickListener
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    private var values: List<Category> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    fun setCategories(category: List<Category>) {
        values = category
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recycler_ic_category)
        val categoryNameText: TextView = view.findViewById(R.id.category_name)

        fun bind(category: Category) {
            categoryNameText.text = category.name

            val childLayoutManager = LinearLayoutManager(
                itemView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            ).also { it.initialPrefetchItemCount = category.moviesInCategory.size }
            this.recyclerView.apply {
                layoutManager = childLayoutManager
                adapter = MoviesAdapter(
                    R.layout.movie_cardview_item,
                    clickListener
                ).also { it.setMovies(category.moviesInCategory) }
                setRecycledViewPool(viewPool)
            }
            itemView.setOnClickListener {
                categoryClickListener.onCategoryClicked(category)
            }
        }
    }
}

interface OnCategoryClickListener {
    fun onCategoryClicked(category: Category)
}
