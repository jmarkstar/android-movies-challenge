package com.jmarkstar.princestheatre.presentation

import androidx.databinding.ViewDataBinding
import com.jmarkstar.princestheatre.presentation.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HiltTestActivity : BaseActivity<ViewDataBinding>() {

    override fun layoutId() = 0
    override fun navHostFragment() = 0
}
