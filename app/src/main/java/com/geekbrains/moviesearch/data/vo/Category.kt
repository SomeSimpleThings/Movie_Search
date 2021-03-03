package com.geekbrains.moviesearch.data.vo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Category(val name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

data class CategoryWithMovies(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val moviesInCategory: List<Movie>
)
