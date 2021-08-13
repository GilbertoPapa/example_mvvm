package com.gilbertopapa.dev.androidmvvm.presentation.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardyyy.dev.androidmvvm.R
import com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie
import com.ardyyy.dev.androidmvvm.presentation.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_favorite.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {

    private val favViewModel: FavoriteViewModel by viewModel()
    private lateinit var favAdapter: FavoriteAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var listFavMovie = ArrayList<com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        initProcess()
    }

    private fun initProcess() {
        favViewModel.movieData.observe(this@FavoriteActivity, Observer { movieList ->
            if (movieList.isNotEmpty()) {
                if(listFavMovie.isNotEmpty()) listFavMovie.clear()
                listFavMovie.addAll(movieList)
                favAdapter.notifyDataSetChanged()
            } else {
                rvFavorite.visibility = View.GONE
                cl_fav_nodata.visibility = View.VISIBLE
            }
        })
        favViewModel.getAllFavMovies()
    }

    private fun initAdapter() {
        favAdapter = FavoriteAdapter(listFavMovie){
            println("clicked ${it.movieId}")
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRAS_MOVIEID, it.movieId)
            startActivity(intent)
        }
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvFavorite.apply {
            layoutManager = linearLayoutManager
            adapter = favAdapter
        }
    }
}