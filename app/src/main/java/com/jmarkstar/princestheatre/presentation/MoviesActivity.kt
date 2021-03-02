package com.jmarkstar.princestheatre.presentation

import android.os.Bundle
import com.jmarkstar.princestheatre.R
import com.jmarkstar.princestheatre.databinding.ActivityMainBinding
import com.jmarkstar.princestheatre.presentation.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesActivity : BaseActivity<ActivityMainBinding>() {

    override fun layoutId() = R.layout.activity_main

    override fun navHostFragment() = R.id.nav_host_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            setupToolbar(toolbar)
        }
    }
}
