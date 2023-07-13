package com.example.appmusic.ui.base

import android.content.Contextimport

android.os.Bundleimport android.os.PersistableBundleimport androidx.appcompat.app.AppCompatActivityimport dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
    }
}