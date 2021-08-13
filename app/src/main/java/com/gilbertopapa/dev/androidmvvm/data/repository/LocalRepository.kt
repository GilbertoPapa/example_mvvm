package com.gilbertopapa.dev.androidmvvm.data.repository

import com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie
import com.gilbertopapa.dev.androidmvvm.data.local.room.MovieAppDao

class LocalRepository(private val movieDao: com.gilbertopapa.dev.androidmvvm.data.local.room.MovieAppDao) {

    fun getAllMovie() = movieDao.getAllFavMovie()
    fun getMovieById(movieID: Int) = movieDao.getFavMovieById(movieID)
    fun insertFavorite(movie: com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie) = movieDao.insert(movie)
    fun deleteFavorite(movie: com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie) = movieDao.delete(movie)
}