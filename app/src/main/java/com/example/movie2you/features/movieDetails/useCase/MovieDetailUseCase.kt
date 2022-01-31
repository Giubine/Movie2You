package com.example.movie2you.features.movieDetails.useCase

import android.app.Application
import com.example.movie2you.extensions.getFullImageUrl
import com.example.movie2you.features.movieDetails.repository.MovieDetailRepository
import com.example.movie2you.useCase.Movie
import com.example.movie2you.utils.ResponseApi

class MovieDetailUseCase(
    application: Application
) {

    private val movieDetailRepository = MovieDetailRepository(application)

    suspend fun getMovieById(movieId: Int): ResponseApi {
        return when(val responseApi = movieDetailRepository.getMovieById(movieId)) {
            is ResponseApi.Success -> {
                val movie = responseApi.data as? Movie
                movie?.poster_path = movie?.poster_path?.getFullImageUrl()
                return ResponseApi.Success(movie)
            }
            is ResponseApi.Error -> {
                responseApi
            }
        }
    }

    suspend fun getMovieByIdFromDb(movieId: Int) =
        movieDetailRepository.getMovieByIdFromDb(movieId)
}



