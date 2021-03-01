package com.geekbrains.moviesearch.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.vo.CategoryWithMovies
import com.geekbrains.moviesearch.ui.BaseMovieFragment


class CategoriesFragment : BaseMovieFragment<List<CategoryWithMovies>>(), OnCategoryClickListener {

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    override fun fragmentViewProvider(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_category, parent, false)
    }

    override fun viewModel(): ViewModel =
        ViewModelProvider(this).get(CategoriesViewModel::class.java)

    override fun recyclerLayoutManagerProvider(): RecyclerView.LayoutManager =
        LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    override fun recyclerAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> =
        CategoryAdapter(this, this)
                as RecyclerView.Adapter<RecyclerView.ViewHolder>

    override fun toDetailsAction(): Int = R.id.action_categoryFragment_to_detailsFragment

    override fun showLoadedState(state: LoadingState<List<CategoryWithMovies>>) {
        if (state is LoadingState.Success)
            (adapter as CategoryAdapter).setCategories(state.value)
    }

    override fun onCategoryClicked(category: CategoryWithMovies) {
        Bundle().also {
            it.putLong("categoryKey", category.category.id)
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_categoryFragment_to_nav_home, it)
        }
    }

}