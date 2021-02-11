package com.geekbrains.moviesearch.ui

import com.geekbrains.moviesearch.R

fun getFavDravableResource(fav: Boolean) = when (fav) {
    true -> R.drawable.ic_baseline_favorite_24
    false -> R.drawable.ic_baseline_favorite_border_24
}

fun getWatchlistDravableResource(watch: Boolean) = when (watch) {
    true -> R.drawable.ic_baseline_playlist_add_check_24
    false -> R.drawable.ic_baseline_playlist_add_24
}