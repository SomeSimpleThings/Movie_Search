package com.geekbrains.moviesearch.data.vo


import com.google.gson.annotations.SerializedName

data class Page(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movies: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)