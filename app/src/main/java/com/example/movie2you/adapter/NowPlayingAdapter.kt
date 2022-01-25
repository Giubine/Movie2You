package com.example.movie2you.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie2you.R
import com.example.movie2you.databinding.WatchCardItemBinding
import com.example.movie2you.useCase.Result


class NowPlayingAdapter(

    private val onClickListener: (movie: Result) -> Unit
) : PagedListAdapter<Result, NowPlayingAdapter.ViewHolder>(Result.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WatchCardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    class ViewHolder(
        private val binding: WatchCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            movie: Result?,
            onClickListener: (movie: Result) -> Unit,
        ) {
            with(binding) {
                movie?.let {
                    tvWatchTitle.text = movie.title
                    cvWatch.setOnClickListener {
                        onClickListener(movie)
                    }
                    Glide
                        .with(itemView.context)
                        .load(movie.posterPath)
                        .placeholder(R.drawable.no_image)
                        .into(ivWatchImage)
                }
            }
        }
    }
}