package com.geekbrains.moviesearch.data.local

import androidx.room.*
import com.geekbrains.moviesearch.data.vo.Category
import com.geekbrains.moviesearch.data.vo.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun allMovies(): List<Movie>

    @Query("SELECT * FROM Movie WHERE favourite = :fav")
    fun favouriteMovies(fav: Boolean = true): List<Movie>

    @Query("SELECT * FROM Movie WHERE inWatchList = :watch")
    fun watchlistMovies(watch: Boolean = true): List<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: Movie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: List<Movie>)

    @Update
    fun update(entity: Movie)

    @Update
    fun update(entity: List<Movie>)

    @Delete
    fun delete(entity: Movie)

}
