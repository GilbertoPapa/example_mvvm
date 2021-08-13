package com.gilbertopapa.dev.androidmvvm.presentation.favorite

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardyyy.dev.androidmvvm.R
import com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_item_movie.view.*

class FavoriteAdapter(
    private val obj: List<com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie>,
    private var onClick: (com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.list_item_movie, parent, false)
        return ContentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return obj.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ContentViewHolder).bind(obj[position], holder.itemView.context)
    }

    inner class ContentViewHolder(mView: View) :
        RecyclerView.ViewHolder(mView) {


        fun bind(
            item: com.gilbertopapa.dev.androidmvvm.data.local.entity.FavoriteMovie,
            mContext: Context
        ) {
            with(itemView) {
                tv_title.text = item.title
//                val rate: Double = movie.vote_average / 2
//                ratingBar.rating = rate.toFloat()

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500/${item.posterPath}")
                    .transform(CenterCrop(), RoundedCorners(8))
                    .placeholder(ColorDrawable(Color.GRAY))
                    .apply(RequestOptions())
                    .into(iv_poster)

                tv_releasedate.text = item.releaseDate
                tv_overview.text = item.overview
                card_movieitem.setOnClickListener {
                    onClick.invoke(item)
                }
            }

        }


//        fun bind(
//            item: FavoriteMovie,
//            mContext: Context
//        ) {
//            itemView.apply {
//                iv_poster.load("https://image.tmdb.org/t/p/w500/${item.posterPath}") {
//                    crossfade(true)
//                    placeholder(createCircularProgress(mContext))
//                }
//                tv_title.text = item.title
//                tv_releasedate.text = item.releaseDate
//                tv_overview.text = item.overview
//                card_movieitem.setOnClickListener {
//                    onClick.invoke(item)
//                }
//            }
//        }
    }
}