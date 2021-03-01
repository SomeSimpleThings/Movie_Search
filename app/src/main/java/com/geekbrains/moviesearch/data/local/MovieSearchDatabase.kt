package com.geekbrains.moviesearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geekbrains.moviesearch.data.vo.*

@Database(
    entities = arrayOf(
        Movie::class,
        Category::class,
        BelongsToCollection::class,
        Genre::class,
        ProductionCompany::class
    ),
    version = 1,
    exportSchema = false
)
abstract class MovieSearchDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
