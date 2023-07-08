package com.example.appmusic.ui.main

import androidx.navigation.NavController

class MainActivity : BaseBindingActivity<ActivityMainBinding?, MainViewModel?>() {
    var navHostFragment: NavHostFragment? = null
    var navController: NavController? = null
    val layoutId: Int
        get() = R.layout.activity_main

    fun setupView(savedInstanceState: Bundle?) {
        navHostFragment =
            getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.getNavController()
        val intent = Intent(this, YoutubeService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    fun setupData() {}
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun onDestroy() {
        super.onDestroy()
    }

    protected val viewModel: Class<MainViewModel>
        protected get() = MainViewModel::class.java
}