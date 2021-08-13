package com.gilbertopapa.dev.androidmvvm.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardyyy.dev.androidmvvm.data.models.response.MovieData
import com.ardyyy.dev.androidmvvm.data.repository.MovieRepository
import com.ardyyy.dev.androidmvvm.utils.MovieCategories
import com.ardyyy.dev.androidmvvm.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    val movieData = MutableLiveData<UiState<MovieData>>()

    fun getMovieData(category: MovieCategories, page: Int){
        movieData.value = UiState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                try {
                    val result = movieRepository.getMovieData(category.value, page)
                    movieData.postValue(UiState.Success(result))
                } catch (e: Exception) {
                    movieData.postValue(UiState.Error(e))
                }
            }
        }
    }

}