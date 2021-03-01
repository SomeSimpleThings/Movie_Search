package com.geekbrains.moviesearch.data

import androidx.lifecycle.MutableLiveData
import com.geekbrains.moviesearch.AppExecutors
import com.geekbrains.moviesearch.data.local.MovieDao
import com.geekbrains.moviesearch.data.remote.TmdbApiService
import com.geekbrains.moviesearch.data.vo.Category
import com.geekbrains.moviesearch.data.vo.CategoryWithMovies
import com.geekbrains.moviesearch.data.vo.Movie
import com.geekbrains.moviesearch.data.vo.Page
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


interface MovieRepository {

    fun getMovies(
        movieListFilter: MovieListFilter = MovieListFilter.All,
        showAdult: Boolean
    ): MutableLiveData<LoadingState<List<Movie>>>

    fun getMovieById(id: Int): MutableLiveData<LoadingState<Movie>>

    fun getCategories(): MutableLiveData<LoadingState<List<CategoryWithMovies>>>
    fun getCategoryById(id: Long): MutableLiveData<LoadingState<CategoryWithMovies>>
}

class RepositoryImpl(
    private val localMoviesDao: MovieDao,
    private val appExecutors: AppExecutors = AppExecutors(),
    val moviesLoadingState: MutableLiveData<LoadingState<List<Movie>>> = MutableLiveData(),
    val movieLoadingState: MutableLiveData<LoadingState<Movie>> = MutableLiveData(),
    val categoriesLoadingState: MutableLiveData<LoadingState<List<CategoryWithMovies>>> = MutableLiveData(),
    val categoryLoadingState: MutableLiveData<LoadingState<CategoryWithMovies>> = MutableLiveData()

) : MovieRepository {
    private val remoteApi: TmdbApiService by lazy {
        TmdbApiService.create()
    }

    override fun getMovies(
        movieListFilter: MovieListFilter,
        showAdult: Boolean
    ): MutableLiveData<LoadingState<List<Movie>>> {
        moviesLoadingState.postValue(LoadingState.Loading)
        performMoviesLoad(movieListFilter)
        return moviesLoadingState
    }

    private fun performMoviesLoad(movieListFilter: MovieListFilter) {
        appExecutors.diskIO().execute {
            when (movieListFilter) {
                MovieListFilter.All -> {
                    if (localMoviesDao.getAllMovies().isEmpty()) {
                        updateMoviesFromNetwork()
                    } else moviesLoadingState.postValue(LoadingState.Success(localMoviesDao.getAllMovies()))
                }
                MovieListFilter.Favourites -> moviesLoadingState.postValue(
                    LoadingState.Success(localMoviesDao.favouriteMovies())
                )
                MovieListFilter.Watchlist -> moviesLoadingState.postValue(
                    LoadingState.Success(localMoviesDao.watchlistMovies())
                )
            }
        }
    }

    private fun updateMoviesFromNetwork() {
        appExecutors.networkIO().execute {
            remoteApi.getDiscover().enqueue(object : Callback<Page> {
                override fun onFailure(call: Call<Page>, t: Throwable) {
                    moviesLoadingState.postValue(LoadingState.Error(t))
                }

                override fun onResponse(call: Call<Page>, response: Response<Page>) {
                    response.body()?.let {
                        localMoviesDao.insertMovies(it.movies)
                        moviesLoadingState.postValue(LoadingState.Success(localMoviesDao.getAllMovies()))
                    }
                }
            })
        }
    }


    override fun getMovieById(id: Int): MutableLiveData<LoadingState<Movie>> {
        appExecutors.diskIO().execute {
            movieLoadingState.postValue(LoadingState.Loading)
            localMoviesDao.getMovieById(id)?.let {
                movieLoadingState.postValue(LoadingState.Success(it))
            } ?: remoteApi.getMovieById(id).enqueue(object : Callback<Movie> {
                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    movieLoadingState.postValue(LoadingState.Error(t))
                }

                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    response.body()?.let {
                        localMoviesDao.insertMovie(it)
                        movieLoadingState.postValue(LoadingState.Success(it))
                    }
                }
            })
        }
        return movieLoadingState
    }

    override fun getCategories()
            : MutableLiveData<LoadingState<List<CategoryWithMovies>>> {
        categoriesLoadingState.postValue(LoadingState.Loading)
        performCategoriesLoad()
        return categoriesLoadingState
    }

    private fun performCategoriesLoad() {
        appExecutors.diskIO().execute {
            if (localMoviesDao.getCategoriesWithMovies().isEmpty()) {
                updateCategoryFromNetwork(remoteApi.getDiscoverSortedBy(), Category("Most popular"))
                updateCategoryFromNetwork(
                    remoteApi.getDiscoverSortedBy(sortBy = "vote_average.desc"),
                    Category("Most rated")
                )
                updateCategoryFromNetwork(remoteApi.getTrending(), Category("Trending"))
            } else categoriesLoadingState.postValue(
                LoadingState.Success(localMoviesDao.getCategoriesWithMovies())
            )
        }
    }

    private fun updateCategoryFromNetwork(apiCall: Call<Page>, category: Category) {
        appExecutors.networkIO().execute {
            apiCall.enqueue(object : Callback<Page> {
                override fun onFailure(call: Call<Page>, t: Throwable) {
                    categoriesLoadingState.postValue(LoadingState.Error(t))
                }

                override fun onResponse(call: Call<Page>, response: Response<Page>) {
                    response.body()?.let {
                        val categoryWithMovies =
                            CategoryWithMovies(category, it.movies)
                        localMoviesDao.insertCategoryWithMovies(categoryWithMovies)
                        categoriesLoadingState.postValue(
                            LoadingState.Success(localMoviesDao.getCategoriesWithMovies())
                        )
                    }
                }
            })
        }
    }

    override fun getCategoryById(id: Long)
            : MutableLiveData<LoadingState<CategoryWithMovies>> {
        appExecutors.diskIO().execute {
            if (id != -1L) {
                localMoviesDao.getCategoryWithMoviesById(id)?.let {
                    categoryLoadingState.postValue(
                        LoadingState.Success(it)
                    )
                }
            }
        }
        return categoryLoadingState
    }


    fun updateMovie(movie: Movie) {
        appExecutors.diskIO().execute {
            localMoviesDao.updateMovie(movie)
            movieLoadingState.postValue(LoadingState.Success(movie))
        }
    }

    fun updateCategory(id: Long) {
        appExecutors.diskIO().execute {
            localMoviesDao.getCategoryWithMoviesById(id)?.let {
                categoryLoadingState.postValue(LoadingState.Success(it))
            }
        }
    }
}

sealed class MovieListFilter {
    object All : MovieListFilter()
    object Favourites : MovieListFilter()
    object Watchlist : MovieListFilter()
}