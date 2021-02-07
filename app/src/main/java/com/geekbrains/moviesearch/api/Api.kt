package com.geekbrains.moviesearch.api

import com.geekbrains.moviesearch.BuildConfig

class Api {
    companion object ApiKey {
        fun getKey() = BuildConfig.TMDB_API_KEY
    }
}