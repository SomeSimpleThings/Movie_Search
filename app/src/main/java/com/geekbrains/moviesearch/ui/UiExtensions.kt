package com.geekbrains.moviesearch.ui

import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.geekbrains.moviesearch.R
import com.google.android.material.snackbar.Snackbar

fun getFavDravableResource(fav: Boolean) =
    if (fav) R.drawable.ic_baseline_favorite_24
    else R.drawable.ic_baseline_favorite_border_24


fun getWatchlistDravableResource(watch: Boolean) =
    if (watch) R.drawable.ic_baseline_playlist_add_check_24
    else R.drawable.ic_baseline_playlist_add_24


fun <T : View> T.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun <T : View> T.hide(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

inline fun <T : View> T.showIf(predicate: (T) -> Boolean): T {
    if (predicate(this)) {
        show()
    } else {
        hide()
    }
    return this
}

fun <T : View> T.showActionSnackbar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

fun <T : View> T.showActionSnackbar(
    text: String,
    actionText: Int,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

fun <T : View> T.showSnackbar(
    text: Int,
    length: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, text, length).show()
}


fun <T : View> T.showSnackbar(
    text: String,
    length: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, text, length).show()
}

fun setGlobalNightMode(nightMode: Boolean) {
    AppCompatDelegate.setDefaultNightMode(
        if (nightMode) AppCompatDelegate.MODE_NIGHT_YES
        else AppCompatDelegate.MODE_NIGHT_NO
    )
}