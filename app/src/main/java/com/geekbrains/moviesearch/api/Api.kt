package com.geekbrains.moviesearch.api

import com.geekbrains.moviesearch.BuildConfig
import com.geekbrains.moviesearch.data.vo.Movie
import com.geekbrains.moviesearch.data.vo.Page
import com.google.gson.Gson
import java.net.URL
import java.nio.charset.Charset
import javax.net.ssl.HttpsURLConnection


const val TMDB_API_URL = "https://api.themoviedb.org/3"
const val TMDB_API_IMAGE_URL = "https://image.tmdb.org/t/p/w342"
const val TMDB_DISCOVER_MOVIE = "/discover/movie"
const val TMDB_TRENDING_TODAY = "/trending/movie/day"
const val TMDB_MOVIE_BY_ID = "/movie"


class Api {
    companion object TmdbApiConstants {
        fun getDiscoverUrl(page: Int = 1, apiKey: String = BuildConfig.TMDB_API_KEY) =
            "$TMDB_API_URL$TMDB_DISCOVER_MOVIE?api_key=$apiKey&language=ru-RU&page=&$page"

        fun getTrendingUrl(page: Int = 1, apiKey: String = BuildConfig.TMDB_API_KEY) =
            "$TMDB_API_URL$TMDB_TRENDING_TODAY?api_key=$apiKey&language=ru-RU&page=&$page"

        fun getMovieByIdUrl(id: Int, apiKey: String = BuildConfig.TMDB_API_KEY) =
            "$TMDB_API_URL$TMDB_MOVIE_BY_ID/$id?api_key=$apiKey&language=ru-RU"

        fun getImageUrl(path: String) =
            "$TMDB_API_IMAGE_URL$path"

    }

    fun getDiscoverPage(pageNum: Int = 1): Page? {
        return URL(getDiscoverUrl(pageNum)).getResponseText()?.let {
            Gson().fromJson(it, Page::class.java)
        }
    }

    fun getTrendingPage(pageNum: Int = 1): Page? {
        return URL(getTrendingUrl(pageNum)).getResponseText()?.let {
            Gson().fromJson(it, Page::class.java)
        }
    }

    fun getMovieById(id: Int): Movie? {
        return URL(getMovieByIdUrl(id)).getResponseText()?.let {
            Gson().fromJson(it, Movie::class.java)
        }
    }
}

fun URL.getResponseText(): String? {
    return kotlin.runCatching {
        (openConnection() as HttpsURLConnection).run {
            requestMethod = "GET"
            readTimeout = 10000
            inputStream.bufferedReader(Charset.defaultCharset())
                .use { it.readText() }
        }
    }.getOrNull()
}