package com.example.movie2you.features.movieDetails.repository.Home.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.movie2you.useCase.Movie

class HomeDataSourceFactory(
    private val tmdbDataSource: HomePageKeyedDataSource,
) : DataSource.Factory<Int, Movie>() {

    private val tmdbLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Movie>>()
    override fun create(): DataSource<Int, Movie> {
        tmdbLiveDataSource.postValue(tmdbDataSource)
        return tmdbDataSource
    }

    fun getLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, Movie>> {
        return tmdbLiveDataSource
    }
}