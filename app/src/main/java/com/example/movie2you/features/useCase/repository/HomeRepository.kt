package com.example.movie2you.features.useCase.repository

import android.app.Application
import com.example.movie2you.api.ApiService
import com.example.movie2you.base.BaseRepository
import com.example.movie2you.database.Mobile2YouDataBase
import com.example.movie2you.modelDB.GenreDb
import com.example.movie2you.modelDB.MovieDb
import com.example.movie2you.modelDB.MovieGenreCrossRef
import com.example.movie2you.modelDB.MovieWithGenres
import com.example.movie2you.utils.ResponseApi

class HomeRepository(
    val application: Application
) : BaseRepository() {

    suspend fun getNowPlayingMovies(page: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getNowPlayingMovies(page)
        }
    }

    suspend fun getPopularMovies(): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getPopularMovies()
        }
    }

    suspend fun getMovieById(id: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getMovieById(id)
        }
    }

    suspend fun getGenres(): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getGenres()
        }
    }

    suspend fun saveGenresDatabase(genres: List<Genre>?) {
        genres?.let { genresApi ->
            val genresDb: MutableList<GenreDb> = mutableListOf()

            genresApi.forEach {
                genresDb.add(it.toGenreDb())
            }

            Mobile2YouDataBase
                .getDatabase(application)
                .genreDao()
                .insertAllGenres(
                    genresDb
                )
        }
    }

    suspend fun saveMoviesDb(movies: List<Result>) {
        val moviesDb: MutableList<MovieDb> = mutableListOf()

        movies.forEach { movie ->
            movie.genre_ids?.forEach { genreId ->
                val movieGenreCrossRef = MovieGenreCrossRef(
                    genreId = genreId,
                    movieId = movie.id ?: -1
                )
                Mobile2YouDataBase
                    .getDatabase(application)
                    .movieGenreDao()
                    .insertMovieGenre(movieGenreCrossRef)
            }
            moviesDb.add(movie.toMovieDb())
        }

        Mobile2YouDataBase
            .getDatabase(application)
            .movieDao()
            .insertAllMovies(
                moviesDb
            )
    }

    suspend fun getNowPlayingMoviesFromDb(): List<MovieWithGenres> {
        val movieDao = Mobile2YouDataBase.getDatabase(application).movieDao()
        return movieDao.getAllMovies()
    }
}