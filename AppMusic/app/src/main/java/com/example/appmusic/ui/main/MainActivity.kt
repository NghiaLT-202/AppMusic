package com.example.appmusic.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.appmusic.R
import com.example.appmusic.databinding.ActivityMainBinding
import com.example.appmusic.ui.base.BaseBindingActivity

class MainActivity : BaseBindingActivity<ActivityMainBinding, MainViewModel>() {
    private val navHostFragment: NavHostFragment? by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
    }
    val navController: NavController by lazy { (navHostFragment?.navController as NavController) }
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun setupView(savedInstanceState: Bundle?) {
    }
    override fun setupData() {
    }
    override fun getViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }
}