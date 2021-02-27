package com.jmarkstar.princestheatre.presentation.movie_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.jmarkstar.princestheatre.R
import com.jmarkstar.princestheatre.presentation.common.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {

    private val movieListViewModel: MovieListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieListViewModel.movies.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    Timber.v("::it's loading::")
                }
                is Resource.Success -> {
                    Timber.v("::movies:: -> ${it.data}")
                }
                is Resource.Error -> {
                    Timber.v("showError: ${it.message}")
                }
            }
        }
        /*
        movieListViewModel.loadMovies()

        movieListViewModel.showProgress.observe(this) {
            Timber.v("showProgress: $it")
        }

        movieListViewModel.showError.observe(this) {
            Timber.v("showError: $it")
        }

        movieListViewModel.showContent.observe(this) {
            Timber.v("showContent: $it")
        }

        movieListViewModel.movies.observe(this) {
            Timber.v("movies: $it")
        }

        movieListViewModel.errorMessage.observe(this) {
            Timber.v("errorMessage: $it")
        }*/
    }
}
