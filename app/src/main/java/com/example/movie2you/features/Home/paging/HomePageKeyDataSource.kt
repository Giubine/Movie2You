package com.example.movie2you.features.movieDetails.repository.Home.paging

import androidx.paging.PageKeyedDataSource
import com.example.movie2you.features.movieDetails.repository.HomeRepository
import com.example.movie2you.useCase.HomeUseCase
import com.example.movie2you.useCase.Movie
import com.example.movie2you.useCase.NowPlaying
import com.example.movie2you.useCase.Result
import com.example.movie2you.utils.ConstantApp.Home.FIRST_PAGE
import com.example.movie2you.utils.ResponseApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomePageKeyedDataSource(
    private val homeRepository: HomeRepository,
    private val homeUseCase: HomeUseCase,
) : PageKeyedDataSource<Int, Result>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>,
    ) {
        CoroutineScope(IO).launch {
            val movies: List<Result> = callNowPlayingMoviesApi(FIRST_PAGE)
            homeUseCase.saveMoviesDb(movies)
            callback.onResult(movies, null, FIRST_PAGE + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        loadData(params.key, params.key - 1, callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        loadData(params.key, params.key + 1, callback)
    }

    private fun loadData(page: Int, nextPage: Int, callback: LoadCallback<Int, Result>) {
        CoroutineScope(IO).launch {
            val movies: List<Result> = callNowPlayingMoviesApi(page)
            callback.onResult(movies, nextPage)
        }
    }

    private suspend fun callNowPlayingMoviesApi(page: Int): List<Result> {
        return when (
            val response = homeRepository.getNowPlayingMovies(page)
        ) {
            is ResponseApi.Success -> {
                val list = response.data as? NowPlaying
                homeUseCase.setupMoviesList(list)
            }
            is ResponseApi.Error -> {
                listOf()
            }
        }
    }
}