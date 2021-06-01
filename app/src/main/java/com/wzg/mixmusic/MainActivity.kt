package com.wzg.mixmusic

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hi.dhl.binding.viewbind
import com.wzg.library.base.BaseActivity
import com.wzg.mixmusic.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private val binding: ActivityMainBinding by viewbind()

    override fun initViews() {
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }

    override fun initData() {

    }

    override fun initObserver() {
    }

}