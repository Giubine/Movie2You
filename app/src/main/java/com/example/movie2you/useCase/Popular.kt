package com.example.movie2you.useCase

data class Popular(
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)