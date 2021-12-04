package com.example.movie2you.features.useCase.useCase

import com.example.movie2you.modelDB.GenreDb

data class Genre (
    val id: Int,
                  val name: String
)

fun Genre.toGenreDb(): GenreDb {
    return GenreDb(
        id = this.id,
        name = this.name,
        xpto = "xpto"
    )
}