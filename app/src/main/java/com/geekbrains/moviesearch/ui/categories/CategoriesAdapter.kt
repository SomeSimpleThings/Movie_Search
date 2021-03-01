package com.geekbrains.moviesearch.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.data.vo.CategoryWithMovies
import com.geekbrains.moviesearch.databinding.CategoryRecyclerViewItemBinding
import com.geekbrains.moviesearch.ui.MoviesAdapter
import com.geekbrains.moviesearch.ui.OnMovieItemClickListener

class CategoryAdapter(
    val categoryClickListener: OnCategoryClickListener,
    val clickListener: OnMovieItemClickListener
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    private var values: List<CategoryWithMovies> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
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

    inner class ViewHolder(private val binding: CategoryRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(categoryWithMovies: CategoryWithMovies) {
            binding.categoryName.text = categoryWithMovies.category.name
            val childLayoutManager = LinearLayoutManager(
                itemView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            ).also { it.initialPrefetchItemCount = categoryWithMovies.moviesInCategory.size }
            binding.recyclerIcCategory.apply {
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
