package com.jmarkstar.princestheatre.presentation.movie_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jmarkstar.princestheatre.R
import com.jmarkstar.princestheatre.databinding.FragmentMovieListBinding
import com.jmarkstar.princestheatre.domain.models.MovieModel
import com.jmarkstar.princestheatre.presentation.common.BaseFragment
import com.jmarkstar.princestheatre.presentation.common.Resource
import com.jmarkstar.princestheatre.presentation.common.extensions.invisible
import com.jmarkstar.princestheatre.presentation.common.extensions.stopRefreshing
import com.jmarkstar.princestheatre.presentation.common.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding>() {

    @Inject
    lateinit var movieAdapter: MovieAdapter

    private val movieColumns = 3

    override fun layoutId() = R.layout.fragment_movie_list

    private val movieListViewModel: MovieListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter.onItemClick = {
            findNavController().navigate(
                MovieListFragmentDirections.actionListToDetails(it.title)
            )
        }

        binding.apply {
            rvMovies.layoutManager = StaggeredGridLayoutManager(
                movieColumns,
                StaggeredGridLayoutManager.VERTICAL
            )
            rvMovies.adapter = movieAdapter

            movieRefresher.setOnRefreshListener {
                movieAdapter.setMovies(emptyList())
                movieListViewModel.loadMovies()
            }
        }

        movieListViewModel.movies.observe(viewLifecycleOwner) {
            binding.apply {

                when (it) {
                    is Resource.Loading -> processLoading()

                    is Resource.Success -> processSuccess(it.data)

                    is Resource.Error -> processError(it.message)
                }
            }
        }
    }

    private fun processLoading() = binding.apply {
        rvMovies.invisible()
        tvErrorMessage.invisible()
        if (!movieRefresher.isRefreshing) {
            pgLoading.visible()
        }
    }

    private fun processSuccess(movies: List<MovieModel>?) = binding.apply {
        pgLoading.invisible()
        tvErrorMessage.invisible()
        rvMovies.visible()
        movieAdapter.setMovies(movies)
        if (movieRefresher.isRefreshing) {
            movieRefresher.stopRefreshing()
        }
    }

    private fun processError(message: String?) = binding.apply {
        pgLoading.invisible()
        rvMovies.invisible()
        tvErrorMessage.visible()
        tvErrorMessage.text = message
        if (movieRefresher.isRefreshing) {
            movieRefresher.stopRefreshing()
        }
    }
}
