package com.example.movie2you.features.movieDetails.repository.Home.paging

import androidx.paging.PageKeyedDataSource
import com.example.movie2you.features.movieDetails.repository.HomeRepository
import com.example.movie2you.useCase.HomeUseCase
import com.example.movie2you.useCase.Movie
import com.example.movie2you.useCase.NowPlaying
import com.example.movie2you.useCase.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomePageKeyedDataSource(
    private val homeRepository: HomeRepository,
    private val homeUseCase: HomeUseCase,
) : PageKeyedDataSource<Int, Movie>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>,
    ) {
        CoroutineScope(IO).launch {
            val movies: List<Movie> = callNowPlayingMoviesApi(FIRST_PAGE)
            homeUseCase.saveMoviesDb(movies)
            callback.onResult(movies, null, FIRST_PAGE + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        loadData(params.key, params.key - 1, callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        loadData(params.key, params.key + 1, callback)
    }

    private fun loadData(page: Int, nextPage: Int, callback: LoadCallback<Int, Movie>) {
        CoroutineScope(IO).launch {
            val movies: List<Movie> = callNowPlayingMoviesApi(page)
            homeUseCase.saveMoviesDb(movies)
            callback.onResult(movies, nextPage)
        }
    }

    private suspend fun callNowPlayingMoviesApi(page: Int): List<Result> {
        return when (
            val response = homeRepository.getNowPlayingMovies(page)
        ) {
            else -> {
                val list = response.data as? NowPlaying
                homeUseCase.setupMoviesList(list)
            }
        }
    }
}