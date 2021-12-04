package com.example.movie2you.modelDB

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithGenres(
    @Embedded val movieDb: MovieDb,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<GenreDb>
)

fun MovieWithGenres.toResultFromApi(): Result {
    return Result(
        adult = this.movieDb.adult,
        backdrop_path = this.movieDb.backdrop_path,
        genre_ids = this.genres.map { it.id },
        id = this.movieDb.id,
        original_language = this.movieDb.original_language,
        original_title = this.movieDb.original_title,
        overview = this.movieDb.overview,
        popularity = this.movieDb.popularity,
        poster_path = this.movieDb.poster_path,
        release_date = this.movieDb.release_date,
        title = this.movieDb.title,
        video = this.movieDb.video,
        vote_average = this.movieDb.vote_average,
        vote_count = this.movieDb.vote_count
    )
}