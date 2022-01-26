package com.example.movie2you.features.movieDetails.repository.Home.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.movie2you.useCase.Movie
import com.example.movie2you.useCase.Result

class HomeDataSourceFactory(
    private val tmdbDataSource: HomePageKeyedDataSource,
) : DataSource.Factory<Int, Result>() {

    private val tmdbLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Result>>()
    override fun create(): DataSource<Int, Result> {
        tmdbLiveDataSource.postValue(tmdbDataSource)
        return tmdbDataSource
    }

    fun getLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, Result>> {
        return tmdbLiveDataSource
    }
}