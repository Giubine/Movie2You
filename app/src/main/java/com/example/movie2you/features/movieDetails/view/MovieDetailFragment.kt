package com.example.movie2you.features.movieDetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movie2you.base.BaseFragment
import com.example.movie2you.databinding.FragmentMovieDetailBinding
import com.example.movie2you.features.movieDetails.viewModel.MovieDetailViewModel
import com.example.movie2you.modelDb.toMovie
import com.example.movie2you.useCase.Movie
import com.example.movie2you.utils.Command
import com.example.movie2you.utils.ConstantApp.Home.KEY_BUNDLE_MOVIE_ID

abstract class MovieDetailFragment : BaseFragment() {

    private var binding: FragmentMovieDetailBinding? = null

    private val movieId: Int by lazy {
        arguments?.getInt(KEY_BUNDLE_MOVIE_ID) ?: -1
    }

    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            viewModel = ViewModelProvider(it)[MovieDetailViewModel::class.java]

            viewModel.getMovieById(movieId)
            viewModel.command = command

            setupObservables()
        }
    }

    private fun setupObservables() {

        viewModel.onSuccessMovieById.observe(viewLifecycleOwner,{
            it.let {
                movie->
                binding.let { bindingNonNull->
                    with(bindingNonNull){
                        activity?.let { activityNonNull ->
                            Glide.with(activityNonNull)
                                .load(movie.backdrop_path)
                                .into(this!!.ivMovieDetailsPosterImage)
                        }
                        this!!.tvMovieDetailsDescriptionText.text = movie.overview
                    }
                }
             }
        })
        viewModel.onSuccessMovieById.observe(viewLifecycleOwner, {
            setupData(it)
        })

        viewModel.onSuccessMovieDbByIdFromDb.observe(viewLifecycleOwner, {
            setupData(it.toMovie())
        })

        viewModel.command.observe(viewLifecycleOwner, {
            when(it) {
                is Command.Loading -> {

                }
                is Command.Error -> {
                    binding?.contentLayout?.isVisible = false
                    binding?.contentError?.isVisible = true
                    viewModel.getMovieById(movieId)
                }
            }
        })

        binding?.btMovieDetailTryAgain?.setOnClickListener {
            viewModel.getMovieById(movieId)
        }

        binding?.btMovieDetailsBackIcon?.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupData(movie: Movie) {
        binding?.let { bindingNonNull ->
            with(bindingNonNull) {
                contentError.isVisible = false
                contentLayout.isVisible = true

                activity?.let { activityNonNull ->
                    Glide.with(activityNonNull)
                        .load(movie.backdrop_path)
                        .into(ivMovieDetailsPosterImage)
                }
                tvMovieDetailsDescriptionText.text = movie.overview
            }
        }
    }

    override var command: MutableLiveData<Command> = MutableLiveData()
}