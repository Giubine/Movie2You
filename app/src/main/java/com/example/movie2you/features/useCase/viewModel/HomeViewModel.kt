package com.example.movie2you.features.useCase.viewModel

import android.app.Application
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.movie2you.base.BaseViewModel
import com.example.movie2you.features.useCase.repository.HomeRepository
import com.example.movie2you.features.useCase.useCase.HomeUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    application: Application,
) : BaseViewModel(application) {

    var moviesPagedList: LiveData<PagedList<Result>>? = null
    private var watchMoviesLiveDataSource: LiveData<PageKeyedDataSource<Int, Result>>? = null

    private val homeUseCase = HomeUseCase(getApplication())
    private val homeRepository = HomeRepository(getApplication<Application>())

    private val _onGenresLoaded: MutableLiveData<Boolean> =  MutableLiveData()
    val onGenresLoaded: LiveData<Boolean>
        get() = _onGenresLoaded

    private val _onMoviesLoadedFromDb: MutableLiveData<List<Result>> = MutableLiveData()
    val onMoviesLoadedFromDb: LiveData<List<Result>>
        get() = _onMoviesLoadedFromDb

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE).build()

        val homePageKeyedDataSource = HomePageKeyedDataSource(
            homeUseCase = homeUseCase,
            homeRepository = homeRepository
        )
        val homeDataSourceFactory = HomeDataSourceFactory(homePageKeyedDataSource)

        watchMoviesLiveDataSource = homeDataSourceFactory.getLiveDataSource()
        moviesPagedList = LivePagedListBuilder(homeDataSourceFactory, pagedListConfig)
            .build()

    }

    fun getGenres() {
        viewModelScope.launch {
            callApi(
                suspend { homeUseCase.getGenres() },
                onSuccess = {
                    _onGenresLoaded.postValue(true)
                }
            )
        }
    }

    fun getMoviesFromDb() {
        viewModelScope.launch {
            val movieGenreList = homeUseCase.getMoviesFromDb()
            _onMoviesLoadedFromDb.postValue(movieGenreList)
        }
    }

//    fun getMovieById(id: Int) {
//        viewModelScope.launch {
//            callApi(
//                suspend { homeUseCase.getMovieById(id) },
//                onSuccess = {
//                    it
//                }
//            )
//        }
//    }

//    fun getNowPlayingMovies() {
//        viewModelScope.launch {
//            callApi(
//                suspend { homeUseCase.getNowPlayingMovies() },
//                onSuccess = {
//                    val result = it as? List<*>
//                    _onSuccessNowPlaying.postValue(
//                        result?.filterIsInstance<Result>()
//                    )
//                },
//                onError = {
//                    _onCustomErrorNowPlaying.postValue(true)
//                }
//            )
//        }
//    }
//
//    fun getPopularMovies() {
//        viewModelScope.launch {
//            callApi(
//                suspend { homeUseCase.getPopularMovies() },
//                onSuccess = {
//                    _onSuccessPopular.postValue(
//                        it as? Popular
//                    )
//                }
//            )
//        }
//    }
}