package com.example.movie2you.features.movieDetails.repository.Home.paging.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie2you.R
import com.example.movie2you.adapter.NowPlayingAdapter
import com.example.movie2you.base.BaseFragment
import com.example.movie2you.databinding.FragmentMoviesListBinding
import com.example.movie2you.features.movieDetails.repository.Home.paging.viewModel.HomeViewModel
import com.example.movie2you.utils.Command
import com.example.movie2you.utils.ConstantApp.Home.KEY_BUNDLE_MOVIE_ID
import com.google.android.material.snackbar.Snackbar


class MoviesListFragment(
    override var adapter: NowPlayingAdapter,
    override var layoutManager: LinearLayoutManager,
) : BaseFragment() {

    private var binding: FragmentMoviesListBinding? = null
    private lateinit var viewModel: HomeViewModel

    private val nowPlayingAdapter: NowPlayingAdapter by lazy {
       NowPlayingAdapter { movie ->
            val bundle = Bundle()
            bundle.putInt(KEY_BUNDLE_MOVIE_ID, movie.id ?: -1)
            findNavController().navigate(
                R.id.action_homeFragment_to_movieDetailFragment,
                bundle
            )
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            viewModel = ViewModelProvider(it)[HomeViewModel::class.java]
            viewModel.command = command
            viewModel.getGenres()

            setupObservables()
            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() {
        binding?.rvHomeNowPlaying?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = nowPlayingAdapter
        }


    }

    private fun setupRecyclerViewVisibility(
        isListFromInternetShowing: Boolean,
    ) {
        binding?.rvHomeNowPlaying?.isVisible = isListFromInternetShowing
    }

    private fun loadContent() {
        viewModel.moviesPagedList?.observe(viewLifecycleOwner, { list ->
            setupRecyclerViewVisibility(
                isListFromInternetShowing = true
            )
        })
    }

    private fun setupObservables() {

//        viewModel.onSuccessNowPlaying.observe(viewLifecycleOwner, {
//            it?.let{nowPlayingList ->
//                val nowPlayingAdapter = NowPlayingAdapter(
//                    nowPlayingList = nowPlayingList
//                ){movie ->
//                    viewModel.getMovieById(movie.id)
//                }
//
//                binding.let {
//                    with(it){
//                        rvHomeNowPlaying.apply{
//                            layoutManager = GridLayoutManager (context, 2)
//                            adapter = nowPlayingAdapter
//                        }
//                    }
//                }
//            }
//        })

        viewModel.moviesPagedList?.observe(viewLifecycleOwner, {
            nowPlayingAdapter.submitList(it)
        })
        viewModel.onMoviesLoadedFromDb.observe(viewLifecycleOwner, {
            it?.let {
                setupRecyclerViewVisibility(
                    isListFromInternetShowing = false
                )
            }
        })

        viewModel.onGenresLoaded.observe(viewLifecycleOwner, {
            loadContent()
        })

        viewModel.command.observe(viewLifecycleOwner, {
            when (it) {
                is Command.Loading -> {

                }
                is Command.Error -> {
                    binding?.let { bindingNonNull ->
                        Snackbar.make(
                            bindingNonNull.rvHomeNowPlaying,
                            getString(it.error),
                            Snackbar.LENGTH_SHORT
                        ).show()

                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    override var command: MutableLiveData<Command> = MutableLiveData()

}