package com.gilbertopapa.dev.androidmvvm.data.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieItem(
    @Json(name = "original_language")
    val originalLanguage: String = "",
    @Json(name = "title")
    val title: String = "",
    @Json(name = "popularity")
    val popularity: Double = 0.0,
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "overview")
    val overview: String = "",
    @Json(name = "original_title")
    val originalTitle: String = "",
    @Json(name = "poster_path")
    val posterPath: String? = "",
    @Json(name = "release_date")
    val releaseDate: String = "",
    @Json(name = "vote_average")
    val voteAverage: Double = 0.0,

    var isLoading: Boolean = false
)


