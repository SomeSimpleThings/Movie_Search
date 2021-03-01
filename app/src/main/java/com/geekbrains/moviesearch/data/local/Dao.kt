package com.geekbrains.moviesearch.data.local

import androidx.room.*
import com.geekbrains.moviesearch.data.vo.Category
import com.geekbrains.moviesearch.data.vo.CategoryWithMovies
import com.geekbrains.moviesearch.data.vo.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM Movie WHERE id=:id")
    fun getMovieById(id: Int): Movie?

    @Query("SELECT * FROM Movie WHERE favourite = :fav")
    fun favouriteMovies(fav: Boolean = true): List<Movie>

    @Query("SELECT * FROM Movie WHERE inWatchList = :watch")
    fun watchlistMovies(watch: Boolean = true): List<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(entity: Movie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(entity: List<Movie>)

    @Update
    fun updateMovie(entity: Movie)

    @Update
    fun updateMovies(entity: List<Movie>)

    @Delete
    fun deleteMovie(entity: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(entity: Category): Long

    @Transaction
    @Query("SELECT * FROM Category")
    fun getCategoriesWithMovies(): List<CategoryWithMovies>

    @Transaction
    @Query("SELECT * FROM Category WHERE id=:id")
    fun getCategoryWithMoviesById(id: Long): CategoryWithMovies?

    @Transaction
    @Query("SELECT * FROM Category WHERE name=:name")
    fun getCategoryWithMoviesByName(name: String): CategoryWithMovies?

    @Transaction
    fun insertCategoryWithMovies(categoryWithMovies: CategoryWithMovies): Long {
        val id = insertCategory(categoryWithMovies.category)
        categoryWithMovies.moviesInCategory.forEach {
            it.categoryId = id
            insertMovie(it)
        }
        return id
    }
}
