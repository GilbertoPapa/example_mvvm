package com.ardyyy.dev.androidmvvm.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ardyyy.dev.androidmvvm.data.repository.MovieRepository
import com.ardyyy.dev.androidmvvm.utils.MovieCategories
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock


class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var homeViewModel: HomeViewModel
    private val movieRepository = mock(MovieRepository::class.java)

    @Before
    fun before() {
        homeViewModel = HomeViewModel(movieRepository)
    }

    @Test
    fun getMovieData() {
        homeViewModel.getMovieData(MovieCategories.NOWPLAYING, 1)
        Assert.assertNotNull(homeViewModel.movieData.value)
    }
}