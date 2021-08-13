package com.ardyyy.dev.androidmvvm.presentation.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ardyyy.dev.androidmvvm.data.repository.LocalRepository
import com.ardyyy.dev.androidmvvm.data.repository.MovieRepository
import com.ardyyy.dev.androidmvvm.presentation.detail.DetailViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class FavoriteViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailViewModel
    private val movieRepository = Mockito.mock(MovieRepository::class.java)
    private val localRepository = Mockito.mock(LocalRepository::class.java)

    @Before
    fun before() {
        viewModel = DetailViewModel(movieRepository, localRepository)
    }

    @Test
    fun getMovieData() {
        viewModel.getMovieDetail(514847)
        Assert.assertNotNull(viewModel.movieItem.value)
    }

}