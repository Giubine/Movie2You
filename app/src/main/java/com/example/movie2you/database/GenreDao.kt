package com.example.movie2you.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.movie2you.modelDb.GenreDb
import com.example.movie2you.modelDb.GenreWithMovies

interface GenreDao {
    abstract fun insertAllGenres(genresDb: MutableList<GenreDb>)

    @Dao
    interface GenreDao {

        @Transaction
        @Query("SELECT * FROM genre")
        suspend fun getAllMoviesFromGenre(): List<GenreWithMovies>

        @Query("SELECT * FROM genre")
        suspend fun getAllGenres(): List<GenreDb>

        @Query("SELECT * FROM genre WHERE genreId = :genreId")
        suspend fun loadGenreById(genreId: Int): List<GenreDb>

        @Insert(onConflict = REPLACE)
        suspend fun insertAllGenres(moviesList: List<GenreDb>)

        @Insert(onConflict = REPLACE)
        suspend fun insertGenre(genre: GenreDb)

        @Delete
        suspend fun delete(genreDb: GenreDb)
    }
}