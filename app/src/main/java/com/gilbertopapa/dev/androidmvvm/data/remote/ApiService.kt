package com.gilbertopapa.dev.androidmvvm.data.remote

import com.ardyyy.dev.androidmvvm.data.models.response.MovieData
import com.ardyyy.dev.androidmvvm.data.models.response.MovieItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/{category}")
    suspend fun getMovieData(
        @Path("category") category: String,
        @Query("page") page : Int
    ): MovieData

    @GET("movie/{id}")
    suspend fun getMovieItem(@Path("id") id: Int): MovieItem
}