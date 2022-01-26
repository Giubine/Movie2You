package com.example.movie2you.useCase

import android.app.Application
import com.example.movie2you.features.movieDetails.repository.HomeRepository
import com.example.movie2you.utils.ConstantApp.Home.FIRST_PAGE
import com.example.movie2you.utils.ResponseApi

class HomeUseCase(
    private val application: Application,
) {

    private val homeRepository = HomeRepository(application)

    suspend fun getNowPlayingMovies(): ResponseApi {
        return when (val responseApi = homeRepository.getNowPlayingMovies(FIRST_PAGE)) {
            is ResponseApi.Success -> {
                val data = responseApi.data as? NowPlaying
                val result = data?.results?.map {
                    it.backdropPath = it.backdropPath?.getFullImageUrl()
                    it.posterPath = it.posterPath?.getFullImageUrl()
                    it
                }
                ResponseApi.Success(result)
            }
            is ResponseApi.Error -> {
            responseApi
            }
        }
    }

    suspend fun getPopularMovies() =
        homeRepository.getPopularMovies()

    suspend fun getMovieById(id: Int) =
        homeRepository.getMovieById(id)

    fun setupMoviesList(list: NowPlaying?): List<Result> {
        return list?.results?.map {
            it.backdropPath = it.backdropPath?.getFullImageUrl()
            it.posterPath = it.posterPath?.getFullImageUrl()
            it
        }?: listOf()

    }

    suspend fun getGenres(): ResponseApi {
        return when(val response = homeRepository.getGenres()) {
            is ResponseApi.Success -> {
                val genreInfo = response.data as? GenreInfo
                homeRepository.saveGenresDatabase(genreInfo?.genres)
                response
            }
            is ResponseApi.Error -> {
                response
            }
        }
    }

    suspend fun saveMoviesDb(movies: List<Result>) {
        homeRepository.saveMoviesDb(movies)
    }

    suspend fun getMoviesFromDb(): List<Result> {
        val movieGenreList = homeRepository.getNowPlayingMoviesFromDb()
        return movieGenreList.map {
            it.to(ResultApi)
        }
    }
}

}


