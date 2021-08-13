package com.gilbertopapa.dev.androidmvvm.presentation.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardyyy.dev.androidmvvm.R
import com.ardyyy.dev.androidmvvm.data.models.response.MovieItem
import com.ardyyy.dev.androidmvvm.databinding.ActivityHomeBinding
import com.ardyyy.dev.androidmvvm.presentation.base.BaseActivity
import com.ardyyy.dev.androidmvvm.presentation.detail.DetailActivity
import com.ardyyy.dev.androidmvvm.presentation.favorite.FavoriteActivity
import com.ardyyy.dev.androidmvvm.utils.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.android.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity(), View.OnClickListener {

    private val homeViewModel: HomeViewModel by viewModel()
    lateinit var sheetBehavior: BottomSheetBehavior<*>
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private var listMovie = ArrayList<MovieItem>()
    private var loadingItem = MovieItem()
    private var isLoadMore: Boolean = false
    private var page: Int = 1
    private var tempCategory: MovieCategories = MovieCategories.NOWPLAYING
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        initializeBottomSheet()
        initAdapter()
        initListener()
        initProcess()
    }

    private fun initializeBottomSheet() {
        sheetBehavior = BottomSheetBehavior.from(binding.homeBottomSheet.bottomSheet)
        sheetBehavior.setPeekHeight(binding.homeBottomSheet.tvBottomsheetToggle.height, true)
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                println(newState)
            }
        })
        binding.homeBottomSheet.tvBottomsheetToggle.setOnClickListener(this)
        binding.homeBottomSheet.tvPopular.setOnClickListener(this)
        binding.homeBottomSheet.tvUpcoming.setOnClickListener(this)
        binding.homeBottomSheet.tvNowplaying.setOnClickListener(this)
        binding.homeBottomSheet.tvToprated.setOnClickListener(this)
    }

    private fun initAdapter() {
        homeAdapter = HomeAdapter(listMovie) {
            println("clicked ${it.id}")
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRAS_MOVIEID, it.id)
            startActivity(intent)
        }
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvHome.apply {
            layoutManager = linearLayoutManager
            adapter = homeAdapter
        }
    }

    private fun initListener() {
        scrollListener = object : EndlessRecyclerViewScrollListener(2, linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                insertLoadingItem()
                hitApi(false)
            }
        }
        binding.rvHome.addOnScrollListener(scrollListener)
    }

    private fun initProcess() {
        homeViewModel.movieData.observe(this@HomeActivity, Observer {
            when (it) {
                is UiState.Loading -> {
                    println("loading")
                }
                is UiState.Success -> {
                    stopShimmer(binding.rvHome)
                    if (!isLoadMore) {
                        if (!listMovie.isEmpty()) listMovie.clear()
                        it.data.results?.let { it1 -> listMovie.addAll(it1) }
                        homeAdapter.notifyDataSetChanged()
                    } else {
                        removeLoadingItem()

                        it.data.results?.let { it1 -> listMovie.addAll(it1) }
                        it.data.results?.size?.let { it1 ->
                            homeAdapter.notifyItemRangeChanged(
                                (listMovie.size - 1) - it1,
                                it1
                            )
                        }
                    }
                    isLoadMore = true
                }
                is UiState.Error -> {
                    stopShimmer(binding.rvHome)
                    val message = NetworkHelper().getErrorMessage(it.throwable)
                    this@HomeActivity.showShortToast(message)
                }
            }
        })
        hitApi(true)
    }

    private fun hitApi(isInitial: Boolean) {
        if (isInitial) {
            page = 1
            isLoadMore = false
            startShimmer(binding.rvHome)
        } else {
            page += 1
            isLoadMore = true
        }
        Handler().postDelayed(
            {
                homeViewModel.getMovieData(tempCategory, page)
            }, 900
        )
    }

    private fun startShimmer(rv: RecyclerView) {
        val shimmer = binding.homeShimmer.root
        rv.visibility = View.GONE
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
    }

    private fun stopShimmer(rv: RecyclerView) {
        val shimmer = binding.homeShimmer.root
        rv.visibility = View.VISIBLE
        shimmer.visibility = View.GONE
        shimmer.stopShimmer()
    }

    private fun insertLoadingItem() {
        loadingItem.isLoading = true
        listMovie.add(loadingItem)
        homeAdapter.notifyItemInserted(listMovie.size - 1)
    }

    private fun removeLoadingItem() {
        listMovie.removeAt(listMovie.lastIndex)
        val scrollPosition: Int = listMovie.size
        homeAdapter.notifyItemRemoved(scrollPosition)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_bottomsheet_toggle -> {
                if (sheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
                }
            }
            else -> {
                when (v?.id) {
                    R.id.tv_popular -> tempCategory = MovieCategories.POPULAR
                    R.id.tv_upcoming -> tempCategory = MovieCategories.UPCOMING
                    R.id.tv_nowplaying -> tempCategory = MovieCategories.NOWPLAYING
                    R.id.tv_toprated -> tempCategory = MovieCategories.TOPRATED
                }
                hitApi(true)
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
