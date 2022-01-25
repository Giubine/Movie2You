package com.example.movie2you.features.movieDetails.repository

import android.app.Application
import com.example.movie2you.api.ApiService
import com.example.movie2you.base.BaseRepository
import com.example.movie2you.database.MovieYouDataBase
import com.example.movie2you.utils.ResponseApi

class MovieDetailRepository(
    private val application: Application
) : BaseRepository() {

    suspend fun getMovieById(movieId: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getMovieById(movieId)
        }
    }

    suspend fun getMovieByIdFromDb(movieId: Int) =
        MovieYouDataBase.getDatabase(application)
            .movieDao().loadMovieById(movieId)
}