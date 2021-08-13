package com.gilbertopapa.dev.androidmvvm.data.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieData(
    @Json(name = "page")
    val page: Int = 0,
    @Json(name = "total_pages")
    val totalPages: Int = 0,
    @Json(name = "results")
    val results: List<MovieItem>? = null,
    @Json(name = "total_results")
    val totalResults: Int = 0
)