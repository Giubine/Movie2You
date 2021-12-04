package com.example.movie2you.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie2you.R
import com.example.movie2you.features.useCase.useCase.Result
import com.example.movie2you.features.useCase.view.MoviesListFragment

class NowPlayingAdapter(
    private val onClickListener: (movie: Result) -> Unit
) : PagedListAdapter<Result, NowPlayingAdapter.ViewHolder>(com.example.movie2you.features.useCase.useCase.Result.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MoviesListFragment
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    class ViewHolder(
        private val binding: MoviesListFragment
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            movie: com.example.movie2you.features.useCase.useCase.Result?,
            onClickListener: (movie: com.example.movie2you.features.useCase.useCase.Result) -> Unit,
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
                        .placeholder(R.drawable.no_image_available)
                        .into(ivWatchImage)
                }
            }
        }
    }
}