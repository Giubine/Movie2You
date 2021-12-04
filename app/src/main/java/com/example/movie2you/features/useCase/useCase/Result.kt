package com.example.movie2you.features.useCase.useCase

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.example.movie2you.modelDB.MovieDb
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
): Parcelable {

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Result> =
            object : DiffUtil.ItemCallback<Result>() {
                override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }
}

fun Result.toMovieDb(): MovieDb {
    return MovieDb(
        id = this.id,
        adult = this.adult,
        backdrop_path = this.backdropPath,
        original_language = this.originalLanguage,
        original_title = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        poster_path = this.posterPath,
        release_date = this.releaseDate,
        title = this.title,
        video = this.video,
        vote_average = this.voteAverage,
        vote_count = this.voteCount
    )
}