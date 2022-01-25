package com.example.movie2you.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie2you.R
import com.example.movie2you.databinding.WatchCardItemBinding
import com.example.movie2you.useCase.Movie


class NowPlayingDbAdapter(
    private val onClickListener: (movie: Movie) -> Unit
) : ListAdapter<Result, NowPlayingDbAdapter.ViewHolder>(Result.javaClass) {

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WatchCardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    class ViewHolder(
        private val binding: WatchCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            movie: Movie?,
            onClickListener: (movie: Movie) -> Unit,
        ) {
            with(binding) {
                movie?.let {
                    tvWatchTitle.text = movie.title
                    cvWatch.setOnClickListener {
                        onClickListener(movie)
                    }
                    Glide
                        .with(itemView.context)
                        .load(movie.poster_path)
                        .placeholder(R.drawable.no_image)
                        .into(ivWatchImage)
                }
            }
        }
    }
}