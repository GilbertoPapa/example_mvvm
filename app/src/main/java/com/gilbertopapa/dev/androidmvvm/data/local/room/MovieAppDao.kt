package com.gilbertopapa.dev.androidmvvm.data.local.room

import androidx.room.*
import com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie

@Dao
interface MovieAppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favMovie: com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie)

    @Update
    fun update(favMovie: com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie)

    @Delete
    fun delete(favMovie: com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie)

    @Query("SELECT * from favoritemovie ORDER BY movieId ASC")
    fun getAllFavMovie(): List<com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie>

    @Query("SELECT * from favoritemovie WHERE movieId = :movId")
    fun getFavMovieById(movId: Int): com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie
}