package com.jmarkstar.princestheatre.presentation.movie_details

import com.jmarkstar.princestheatre.R
import com.jmarkstar.princestheatre.databinding.FragmentMovieDetailsBinding
import com.jmarkstar.princestheatre.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {

    override fun layoutId() = R.layout.fragment_movie_details
}
