package com.geekbrains.moviesearch.data

import androidx.lifecycle.MutableLiveData
import com.geekbrains.moviesearch.data.local.DummyContent
import com.geekbrains.moviesearch.data.local.MovieDao
import com.geekbrains.moviesearch.data.remote.TmdbApiService
import com.geekbrains.moviesearch.data.vo.Category
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

    fun getCategories(): MutableLiveData<LoadingState<List<Category>>>
    fun getCategoryById(id: Int): MutableLiveData<LoadingState<Category>>
}

class RepositoryImpl(
    private val localMoviesDao: MovieDao,
    val moviesLoadingState: MutableLiveData<LoadingState<List<Movie>>> = MutableLiveData(),
    val movieLoadingState: MutableLiveData<LoadingState<Movie>> = MutableLiveData(),
    val categoriesLoadingState: MutableLiveData<LoadingState<List<Category>>> = MutableLiveData(),
    val categoryLoadingState: MutableLiveData<LoadingState<Category>> = MutableLiveData()

) : MovieRepository {
    private val remoteApi: TmdbApiService by lazy {
        TmdbApiService.create()
    }

    override fun getMovies(
        movieListFilter: MovieListFilter,
        showAdult: Boolean
    ): MutableLiveData<LoadingState<List<Movie>>> {
        moviesLoadingState.postValue(LoadingState.Loading)
        when (movieListFilter) {
            MovieListFilter.All -> {
                if (MovieListFilter.All.list.isEmpty()) {
                    remoteApi.getDiscover().enqueue(object : Callback<Page> {
                        override fun onFailure(call: Call<Page>, t: Throwable) {
                            moviesLoadingState.postValue(LoadingState.Error(t))
                        }

                        override fun onResponse(call: Call<Page>, response: Response<Page>) {
                            response.body()?.let {
                                localMoviesDao.insert(it.movies)
                                moviesLoadingState.postValue(LoadingState.Success(localMoviesDao.allMovies()))
                            }
                        }
                    })
                } else moviesLoadingState.postValue(LoadingState.Success(DummyContent.loadedMovies))
            }
            MovieListFilter.Favourites -> moviesLoadingState.postValue(
                LoadingState.Success(
                    localMoviesDao.favouriteMovies()
                )
            )
            MovieListFilter.Watchlist -> moviesLoadingState.postValue(
                LoadingState.Success(
                    localMoviesDao.watchlistMovies()
                )
            )
        }
        return moviesLoadingState
    }


    override fun getMovieById(id: Int): MutableLiveData<LoadingState<Movie>> {
        movieLoadingState.postValue(LoadingState.Loading)
        MovieListFilter.All.list.firstOrNull { id == it.id }?.let {
            movieLoadingState.postValue(LoadingState.Success(it))
        } ?: remoteApi.getMovieById(id).enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                movieLoadingState.postValue(LoadingState.Error(t))
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                response.body()?.let {
                    movieLoadingState.postValue(LoadingState.Success(it))
                }
            }
        })
        return movieLoadingState
    }

    override fun getCategories()
            : MutableLiveData<LoadingState<List<Category>>> {
        categoriesLoadingState.postValue(LoadingState.Loading)
        if (DummyContent.REMOTE_CATEGORIES.isEmpty()) {
            remoteApi.getDiscoverSortedBy().enqueue(object : Callback<Page> {
                override fun onFailure(call: Call<Page>, t: Throwable) {
                    categoriesLoadingState.postValue(LoadingState.Error(t))
                }

                override fun onResponse(call: Call<Page>, response: Response<Page>) {
                    response.body()?.let {
                        DummyContent.REMOTE_CATEGORIES.add(Category(1, "Most popular", it.movies))
                        categoriesLoadingState.postValue(
                            LoadingState.Success(DummyContent.REMOTE_CATEGORIES)
                        )
                    }
                }
            })
            remoteApi.getDiscoverSortedBy(sortBy = "vote_average.desc")
                .enqueue(object : Callback<Page> {
                    override fun onFailure(call: Call<Page>, t: Throwable) {
                        categoriesLoadingState.postValue(LoadingState.Error(t))
                    }

                    override fun onResponse(call: Call<Page>, response: Response<Page>) {
                        response.body()?.let {
                            DummyContent.REMOTE_CATEGORIES.add(Category(2, "Most rated", it.movies))
                            categoriesLoadingState.postValue(
                                LoadingState.Success(DummyContent.REMOTE_CATEGORIES)
                            )
                        }
                    }
                })
            remoteApi.getTrending().enqueue(object : Callback<Page> {
                override fun onFailure(call: Call<Page>, t: Throwable) {
                    categoriesLoadingState.postValue(LoadingState.Error(t))
                }

                override fun onResponse(call: Call<Page>, response: Response<Page>) {
                    response.body()?.let {
                        DummyContent.REMOTE_CATEGORIES.add(Category(3, "Trending", it.movies))
                        categoriesLoadingState.postValue(
                            LoadingState.Success(DummyContent.REMOTE_CATEGORIES)
                        )
                    }
                }
            })

        } else categoriesLoadingState.postValue(
            LoadingState.Success(DummyContent.REMOTE_CATEGORIES)
        )
        return categoriesLoadingState
    }

    override fun getCategoryById(id: Int)
            : MutableLiveData<LoadingState<Category>> {
        if (id != -1) categoryLoadingState.postValue(
            LoadingState.Success(
                DummyContent.getCategory(
                    id
                )
            )
        )
        return categoryLoadingState
    }


    fun updateMovie(movie: Movie) {
        DummyContent.update(movie)
        movieLoadingState.postValue(LoadingState.Success(movie))
    }

    fun updateCategory(id: Int) {
        categoryLoadingState.postValue(LoadingState.Success(DummyContent.getCategory(id)))
    }
}

sealed class MovieListFilter {
    object All : MovieListFilter()
    object Favourites : MovieListFilter()
    object Watchlist : MovieListFilter()


    val list: List<Movie>
        get() = when (this) {
            is All -> DummyContent.loadedMovies
            is Favourites -> DummyContent.favouritesMovies
            is Watchlist -> DummyContent.watchMovies
        }
}