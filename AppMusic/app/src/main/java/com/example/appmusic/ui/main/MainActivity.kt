package com.example.appmusic.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.appmusic.R
import com.example.appmusic.databinding.ActivityMainBinding
import com.example.appmusic.service.MusicService
import com.example.appmusic.ui.base.BaseBindingActivity
import timber.log.Timber

class MainActivity : BaseBindingActivity<ActivityMainBinding?, MainViewModel>() {
    var navHostFragment: NavHostFragment? = null
    var navController: NavController? = null


    override fun setupView(savedInstanceState: Bundle?) {
        Timber.e("nghialt: onCreatedView")
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment!!.navController
        val intent = Intent(this, MusicService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    override fun setupData() {}
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Timber.e("nghialt: onCreatedView")

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun getLayoutId(): Int {
        return  R.layout.activity_main
    }

    override fun getViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }
}