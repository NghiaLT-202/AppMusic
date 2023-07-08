package com.example.appmusic.ui.base

import android.content.Context
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    protected override fun onResume() {
        super.onResume()
    }

    protected override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
    }
}