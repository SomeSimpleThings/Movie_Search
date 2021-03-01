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
import com.geekbrains.moviesearch.data.vo.CategoryWithMovies

class CategoryAdapter(
    val categoryClickListener: OnCategoryClickListener,
    val clickListener: OnMovieItemClickListener
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    private var values: List<CategoryWithMovies> = mutableListOf()

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

    fun setCategories(category: List<CategoryWithMovies>) {
        values = category
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recycler_ic_category)
        val categoryNameText: TextView = view.findViewById(R.id.category_name)

        fun bind(categoryWithMovies: CategoryWithMovies) {
            categoryNameText.text = categoryWithMovies.category.name

            val childLayoutManager = LinearLayoutManager(
                itemView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            ).also { it.initialPrefetchItemCount = categoryWithMovies.moviesInCategory.size }
            this.recyclerView.apply {
                layoutManager = childLayoutManager
                adapter = MoviesAdapter(
                    R.layout.movie_cardview_item,
                    clickListener
                ).also { it.setMovies(categoryWithMovies.moviesInCategory) }
                setRecycledViewPool(viewPool)
            }
            itemView.setOnClickListener {
                categoryClickListener.onCategoryClicked(categoryWithMovies)
            }
        }
    }
}

interface OnCategoryClickListener {
    fun onCategoryClicked(category: CategoryWithMovies)
}
