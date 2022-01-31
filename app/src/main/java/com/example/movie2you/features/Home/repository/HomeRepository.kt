package com.example.movie2you.features.movieDetails.repository

import android.app.Application
import com.example.movie2you.api.ApiService
import com.example.movie2you.base.BaseRepository
import com.example.movie2you.database.MovieDao
import com.example.movie2you.database.MovieYouDataBase
import com.example.movie2you.modelDb.GenreDb
import com.example.movie2you.modelDb.MovieDb
import com.example.movie2you.modelDb.MovieGenreCrossRef
import com.example.movie2you.modelDb.MovieWithGenres
import com.example.movie2you.useCase.*
import com.example.movie2you.utils.ResponseApi

import retrofit2.Response

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
                genresDb.add(it.toGenreDb)
            }

            MovieYouDataBase
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
            movie.genreIds?.forEach { genreId ->
                val movieGenreCrossRef = MovieGenreCrossRef(
                    genreId = genreId,
                    movieId = movie.id ?: -1
                )
                MovieYouDataBase
                    .getDatabase(application)
                    .movieGenreDao()
                    .insertMovieGenre(movieGenreCrossRef)
            }
        }

        MovieYouDataBase
            .getDatabase(application)
            .movieDao()
            .insertAllMovies(
                moviesDb
            )
    }

    suspend fun getNowPlayingMoviesFromDb(): List<MovieWithGenres> {
        return MovieDao.getAllMovies()
    }
}