package com.geekbrains.moviesearch

import android.app.Application
import androidx.room.Room
import com.geekbrains.moviesearch.data.local.MovieDao
import com.geekbrains.moviesearch.data.local.MovieSearchDatabase

class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: MovieApplication? = null
        private var db: MovieSearchDatabase? = null
        private const val DB_NAME = "movie.db"

        private fun getDatabase(): MovieSearchDatabase {
            if (db == null) {
                synchronized(MovieSearchDatabase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            MovieSearchDatabase::class.java,
                            DB_NAME
                        ).allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!
        }

        fun getMovieDao(): MovieDao {
            return getDatabase().movieDao()
        }
    }

}