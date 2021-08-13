package com.gilbertopapa.dev.androidmvvm.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "error")
    var error: String = "",

    @Json(name = "error_description")
    var errorDesription: String = "",

    @Json(name = "message")
    var message: String = ""
)