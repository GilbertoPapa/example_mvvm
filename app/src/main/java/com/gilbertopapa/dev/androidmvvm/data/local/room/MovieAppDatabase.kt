package com.gilbertopapa.dev.androidmvvm.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie

@Database(entities = [com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie::class], version = 1)
abstract class MovieAppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: com.gilbertopapa.dev.androidmvvm.data.local.room.MovieAppDatabase? = null

        fun getDatabase(context: Context): com.gilbertopapa.dev.androidmvvm.data.local.room.MovieAppDatabase {
            if (com.gilbertopapa.dev.androidmvvm.data.local.room.MovieAppDatabase.Companion.INSTANCE == null) {

                com.gilbertopapa.dev.androidmvvm.data.local.room.MovieAppDatabase.Companion.INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    com.gilbertopapa.dev.androidmvvm.data.local.room.MovieAppDatabase::class.java,
                    "movieapp_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

            }
            return com.gilbertopapa.dev.androidmvvm.data.local.room.MovieAppDatabase.Companion.INSTANCE!!
        }
    }

    abstract fun movieDao(): com.gilbertopapa.dev.androidmvvm.data.local.room.MovieAppDao
}