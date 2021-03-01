package com.geekbrains.moviesearch.data.remote

import com.geekbrains.moviesearch.data.vo.Movie
import com.geekbrains.moviesearch.data.vo.Page
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("discover/movie")
    fun getDiscover(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "ru-RU",
        @Query("include_adult") adult: Boolean = false,
        @Query("page") page: Int = 1
    ): Call<Page>

    @GET("discover/movie")
    fun getDiscoverSortedBy(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "ru-RU",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("vote_count.gte") voteCount: String = "500",
        @Query("include_adult") adult: Boolean = false,
        @Query("page") page: Int = 1
    ): Call<Page>

    @GET("trending/movie/{trendingPeriod}")
    fun getTrending(
        @Path("trendingPeriod") trendingPeriod: String = TrendingPeriod.DAY.name,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1
    ): Call<Page>

    @GET("movie/{id}")
    fun getMovieById(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "ru-RU",
    ): Call<Movie>

    companion object Factory {
        fun create(): TmdbApiService = Retrofit.Builder()
            .client(httpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(TMDB_API_URL)
            .build().create(TmdbApiService::class.java)

        fun httpClient() = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .build();

        fun loggingInterceptor() = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
}

