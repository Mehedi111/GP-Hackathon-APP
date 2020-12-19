package com.ms.gphackathonproject.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gm.lollipop.utils.image.ImageLoader
import com.ms.gphackathonproject.Constants
import com.ms.gphackathonproject.R
import com.ms.gphackathonproject.data.model.Content
import com.ms.gphackathonproject.databinding.LayoutPopularMoviesBinding
import com.ms.gphackathonproject.databinding.LayoutPopularTvSeriesBinding
import com.ms.gphackathonproject.databinding.LayoutTrendingItemsBinding
import com.ms.gphackathonproject.ui.details.ContentDetailsActivity

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */
class HomeChildAdapter(
    private val context: Context,
    private val contentType: String,
    private val dataList: List<Content>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_MOVIE = 1
    private val VIEW_TYPE_TV_SHOW = 2
    private val VIEW_TYPE_TRENDING = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        when (viewType) {

            VIEW_TYPE_MOVIE -> {
                val binding =
                    LayoutPopularMoviesBinding.inflate(LayoutInflater.from(context), parent, false)
                viewHolder = MoviesViewHolder(binding)
            }

            VIEW_TYPE_TV_SHOW -> {
                val binding =
                    LayoutPopularTvSeriesBinding.inflate(
                        LayoutInflater.from(context),
                        parent,
                        false
                    )
                viewHolder = TvViewHolder(binding)
            }
            VIEW_TYPE_TRENDING -> {
                val binding =
                    LayoutTrendingItemsBinding.inflate(LayoutInflater.from(context), parent, false)
                viewHolder = TrendingViewHolder(binding)
            }

            else -> {
                val binding =
                    LayoutPopularMoviesBinding.inflate(LayoutInflater.from(context), parent, false)
                viewHolder = MoviesViewHolder(binding)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val content = dataList[position]

        holder.itemView.setOnClickListener {
            if (contentType == Constants.TYPE_MOVIE || contentType== Constants.TYPE_TV){
                // click listener only for movie and tv show type
                val intent = Intent(context, ContentDetailsActivity::class.java)
                intent.putExtra(Constants.CONTENT_EXTRA, content)
                intent.putExtra(Constants.CONTENT_TYPE, contentType)
                context.startActivity(intent)
            }
        }

        when (holder.itemViewType) {
            VIEW_TYPE_MOVIE -> {
                val movieHolder: MoviesViewHolder =
                    holder as MoviesViewHolder
                ImageLoader.loadImage(
                    movieHolder.binding.previewImage,
                    content.poster_path,
                    Constants.TYPE_MOVIE,
                    R.drawable.default_potrait
                )

                movieHolder.binding.title.text = content.title ?: content.name ?: ""


                content.vote_count?.let {
                    movieHolder.binding.vote.text = ("Vote Count : ${it}")
                }

                content.release_date?.let {
                    movieHolder.binding.date.text = ("Release Date : ${it}")
                }

            }
            VIEW_TYPE_TV_SHOW -> {
                val tvHolder: TvViewHolder =
                    holder as TvViewHolder

                ImageLoader.loadImage(
                    tvHolder.binding.previewImage,
                    content.poster_path,
                    Constants.TYPE_TV,
                    R.drawable.default_potrait
                )

                tvHolder.binding.title.text = content.title ?: content.name ?: ""

            }
            VIEW_TYPE_TRENDING -> {
                val trendingViewHolder: TrendingViewHolder =
                    holder as TrendingViewHolder

                ImageLoader.loadImage(
                    trendingViewHolder.binding.previewImage,
                    content.poster_path,
                    Constants.TYPE_TV,
                    R.drawable.default_potrait
                )


                trendingViewHolder.binding.title.text = content.title ?: content.name ?: ""


                /*content.vote_count?.let {
                    trendingViewHolder.binding.vote.text = ("Vote Count : ${it}")
                }*/
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    private class MoviesViewHolder(val binding: LayoutPopularMoviesBinding) :
        RecyclerView.ViewHolder(binding.root)

    private class TvViewHolder(val binding: LayoutPopularTvSeriesBinding) :
        RecyclerView.ViewHolder(binding.root)

    private class TrendingViewHolder(val binding: LayoutTrendingItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return when (contentType) {
            Constants.TYPE_MOVIE -> VIEW_TYPE_MOVIE
            Constants.TYPE_TV -> VIEW_TYPE_TV_SHOW
            Constants.TYPE_TRENDING -> VIEW_TYPE_TRENDING
            else -> VIEW_TYPE_MOVIE
        }
    }
}