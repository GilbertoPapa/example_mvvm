package com.gilbertopapa.dev.androidmvvm.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie
import com.ardyyy.dev.androidmvvm.data.models.response.MovieItem
import com.ardyyy.dev.androidmvvm.data.repository.LocalRepository
import com.ardyyy.dev.androidmvvm.data.repository.MovieRepository
import com.ardyyy.dev.androidmvvm.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val movieRepository: MovieRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    val movieItem = MutableLiveData<UiState<MovieItem>>()
    val favMovieItem = MutableLiveData<com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie?>()

    fun getMovieDetail(movieID: Int) {
        movieItem.value = UiState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                try {
                    val result = movieRepository.getMovieDetail(movieID)
                    movieItem.postValue(UiState.Success(result))
                } catch (e: Exception) {
                    movieItem.postValue(UiState.Error(e))
                }
            }
        }
    }

    fun getMovieFromDB(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            favMovieItem.postValue(localRepository.getMovieById(movieId))
        }
    }

    fun saveMovieToFav(movie: MovieItem) {
        viewModelScope.launch(Dispatchers.IO) {
            var favMovie = with(movie) {
                com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie(
                    movieId = id,
                    title = this.title,
                    rating = voteAverage,
                    releaseDate = this.releaseDate,
                    overview = this.overview,
                    posterPath = this.posterPath
                )
            }
            localRepository.insertFavorite(favMovie)
        }
    }

    fun deleteMovieFromFav(movie: MovieItem) {
        viewModelScope.launch(Dispatchers.IO) {
            var favMovie = with(movie) {
                com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie(
                    movieId = id
                )
            }
            localRepository.deleteFavorite(favMovie)
        }
    }

}