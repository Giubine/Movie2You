package com.example.movie2you.features.movieDetails.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movie2you.base.BaseViewModel
import com.example.movie2you.features.movieDetails.useCase.MovieDetailUseCase
import com.example.movie2you.modelDb.MovieDb
import com.example.movie2you.useCase.Movie
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    application: Application,
) : BaseViewModel(application) {

    private val movieDetailUseCase = MovieDetailUseCase(
        getApplication<Application>()
    )

    private val _onSuccessMovieById: MutableLiveData<Movie> = MutableLiveData()
    val onSuccessMovieById: LiveData<Movie>
        get() = _onSuccessMovieById

    private val _onSuccessMovieDbByIdFromDb: MutableLiveData<MovieDb> = MutableLiveData()
    val onSuccessMovieDbByIdFromDb: LiveData<MovieDb>
        get() = _onSuccessMovieDbByIdFromDb


    fun getMovieById(movieId: Int) {
        viewModelScope.launch {
            callApi(
                suspend { movieDetailUseCase.getMovieById(movieId) },
                onSuccess = {
                    _onSuccessMovieById.postValue(it as? Movie)
                }
            )
        }
    }

}
