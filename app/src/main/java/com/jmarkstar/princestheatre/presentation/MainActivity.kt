package com.jmarkstar.princestheatre.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jmarkstar.princestheatre.R
import com.jmarkstar.princestheatre.domain.repositories.ProviderRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var repository: ProviderRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
