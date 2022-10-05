package com.tobeyoung.jubgging.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

        initNavigation()

    }

    private fun initNavigation() {
        NavigationUI.setupWithNavController(binding.bottomNavigationView,
            findNavController(R.id.navi_host))
    }
}
