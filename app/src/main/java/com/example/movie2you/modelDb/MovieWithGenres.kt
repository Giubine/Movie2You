package com.example.movie2you.modelDb

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import java.lang.reflect.Type

data class MovieWithGenres(
    @Embedded val movieDb: MovieDb,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<GenreDb>
)

