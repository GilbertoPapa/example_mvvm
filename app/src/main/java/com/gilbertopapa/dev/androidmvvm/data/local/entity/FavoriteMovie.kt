package com.gilbertopapa.dev.androidmvvm.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class FavoriteMovie(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val movieId: Int = 0,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,

    @ColumnInfo(name = "rating")
    val rating: Double = 0.0,

    @ColumnInfo(name = "overview")
    val overview: String? = null,

    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null

) : Parcelable