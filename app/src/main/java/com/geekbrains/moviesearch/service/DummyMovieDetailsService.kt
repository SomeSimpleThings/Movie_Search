package com.geekbrains.moviesearch.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.geekbrains.moviesearch.MOVIE_INFO_KEY
import com.geekbrains.moviesearch.MOVIE_LOADED_FILTER
import com.geekbrains.moviesearch.data.RemoteRepositoryImpl
import com.geekbrains.moviesearch.data.Repository

class DummyMovieDetailsService(val repository: Repository = RemoteRepositoryImpl()) : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.getIntExtra(MOVIE_INFO_KEY, 0)?.let {
            Intent(MOVIE_LOADED_FILTER).also { broadcastIntent ->
                broadcastIntent.putExtra(MOVIE_INFO_KEY, repository.getMovieById(it))
                sendBroadcast(broadcastIntent)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}