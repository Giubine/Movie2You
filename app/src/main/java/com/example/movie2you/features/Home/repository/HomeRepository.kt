package com.example.movie2you.features.movieDetails.repository

import android.app.Application
import com.example.movie2you.base.BaseRepository
import com.example.movie2you.useCase.*

import retrofit2.Response

class HomeRepository(
    var apiService: Application,
    val application: Application,
) : BaseRepository() {

    suspend fun getNowPlayingMovies(): Response<NowPlaying> {
        return apiService.getNowPlayingMovies()

    }

    suspend fun getPopularMovies(): Response<Popular> {
        return apiService.getPopularMovies()
    }

    suspend fun getMovieById(id: Int): Response<Movie> {
        return apiService.getMovieById(id)
    }

    suspend fun getGenres(): Response<GenreInfo> {
        return apiService.getGenres()
    }


}
