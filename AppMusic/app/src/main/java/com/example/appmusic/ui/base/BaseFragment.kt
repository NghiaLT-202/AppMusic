package com.example.appmusic.ui.base

import android.os.Bundleimport

androidx.fragment.app.Fragmentimport dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
open class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}