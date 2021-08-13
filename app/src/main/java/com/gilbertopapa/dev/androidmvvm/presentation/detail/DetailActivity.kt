package com.gilbertopapa.dev.androidmvvm.presentation.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ardyyy.dev.androidmvvm.R
import com.ardyyy.dev.androidmvvm.data.models.response.MovieItem
import com.ardyyy.dev.androidmvvm.utils.NetworkHelper
import com.ardyyy.dev.androidmvvm.utils.UiState
import com.ardyyy.dev.androidmvvm.utils.showShortToast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.list_item_movie.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class DetailActivity : AppCompatActivity() {

    companion object {
        var EXTRAS_MOVIEID: String = "movie_id"
    }

    private var movieId = 0

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var tempMovie: MovieItem
    private var isFavMovie: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        getBundle()
        initListener()
        initProcess()
    }

    private fun initLayout() {
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getBundle() {
        movieId = intent.getIntExtra(EXTRAS_MOVIEID, 0)
    }

    private fun initListener() {
        iv_moviedetail_favorite.setOnClickListener {
            if(isFavMovie){
                isFavMovie = false
                detailViewModel.deleteMovieFromFav(tempMovie)
            } else {
                isFavMovie = true
                detailViewModel.saveMovieToFav(tempMovie)
            }
            refreshFavbutton()
        }
    }

    private fun initProcess() {
        detailViewModel.movieItem.observe(this@DetailActivity, Observer {
            when (it) {
                is UiState.Loading -> {
                    println("loading")
                }
                is UiState.Success -> {
                    stopShimmer(cl_moviedetail_content)
                    bindResult(it)
                }
                is UiState.Error -> {
                    stopShimmer(cl_moviedetail_content)
                    val message = NetworkHelper().getErrorMessage(it.throwable)
                    this@DetailActivity.showShortToast(message)
                }
            }
        })

        detailViewModel.favMovieItem.observe(this@DetailActivity, Observer {
            isFavMovie = it != null
            refreshFavbutton()
        })

        getMovieItem()
    }

    private fun refreshFavbutton() {
        if (isFavMovie) {
            iv_moviedetail_favorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            iv_moviedetail_favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun getMovieItem() {
        startShimmer(cl_moviedetail_content)
        Handler().postDelayed(
            {
                detailViewModel.getMovieDetail(movieId)
            }, 900
        )
    }

    private fun bindResult(it: UiState.Success<MovieItem>) {
        with(it.data) {
//            iv_moviedetail_poster.load("https://image.tmdb.org/t/p/w500/$posterPath") {
//                crossfade(true)
//                placeholder(createCircularProgress(this@DetailActivity))
//            }

            Glide.with(this@DetailActivity)
                .load("https://image.tmdb.org/t/p/w500/$posterPath")
                .transform(CenterCrop(), RoundedCorners(8))
                .placeholder(ColorDrawable(Color.GRAY))
                .apply(RequestOptions())
                .into(iv_moviedetail_poster)

            tv_moviedetail_title.text = title
            tv_moviedetail_releasedate.text = releaseDate
            tv_moviedetail_rating.text = "$voteAverage"
            tv_moviedetail_overview.text = if(overview.isNotBlank()) overview else "-"

        }

        //get fav status
        tempMovie = it.data
        detailViewModel.getMovieFromDB(movieId)
    }

    private fun startShimmer(content: View) {
        val shimmer = detailShimmer as ShimmerFrameLayout
        content.visibility = View.GONE
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
    }

    private fun stopShimmer(content: View) {
        val shimmer = detailShimmer as ShimmerFrameLayout
        content.visibility = View.VISIBLE
        shimmer.visibility = View.GONE
        shimmer.stopShimmer()
    }
}