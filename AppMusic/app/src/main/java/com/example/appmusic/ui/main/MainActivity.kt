package com.example.appmusic.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.appmusic.R
import com.example.appmusic.databinding.ActivityMainBinding
import com.example.appmusic.ui.base.BaseBindingActivity
import timber.log.Timber

class MainActivity : BaseBindingActivity<ActivityMainBinding, MainViewModel>() {
    var navHostFragment: NavHostFragment? = null
    var navController: NavController? = null
    override val layoutId: Int
        get() = R.layout.activity_main


    override fun setupView(savedInstanceState: Bundle?) {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment?.navController

    }

    override fun setupData() {
        viewModel.getAllMusicDetail(this)
        Timber.e("ltnghia"+viewModel.listAllMusicDevice.value?.size)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun getViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }
}