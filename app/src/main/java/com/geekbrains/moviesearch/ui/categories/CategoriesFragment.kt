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
import com.geekbrains.moviesearch.ui.BaseMovieFragment
import com.geekbrains.moviesearch.vo.Category
import com.geekbrains.moviesearch.vo.Movie


class CategoriesFragment : BaseMovieFragment(), OnCategoryClickListener {

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


    override fun showState(state: LoadingState) {
        val loadingLayout =
            activity?.findViewById<View>(R.id.mainFragmentLoadingLayout)
        when (state) {
            is LoadingState.SuccessCategoriesLoad -> {
                loadingLayout?.visibility = View.GONE
                (adapter as CategoryAdapter).setCategories(state.categories)
            }
            is LoadingState.Loading -> {
                loadingLayout?.visibility = View.VISIBLE
            }
            else -> loadingLayout?.visibility = View.GONE
        }
    }

    override fun onMovieClicked(movie: Movie) {
        Bundle().let {
            it.putInt("movieKey", movie.id)
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_categoryFragment_to_detailsFragment, it)
        }
    }

    override fun onCategoryClicked(category: Category) {
        Bundle().let {
            it.putInt("categoryKey", category.id)
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_categoryFragment_to_nav_home, it)
        }
    }

}