package com.gilbertopapa.dev.androidmvvm.data.repository

import com.ardyyy.dev.androidmvvm.data.remote.ApiService

class MovieRepository(private val apiService: ApiService) {

    suspend fun getMovieData(category: String, page : Int) =
        apiService.getMovieData(category, page)

    suspend fun getMovieDetail(movieId: Int) =
        apiService.getMovieItem(movieId)

}