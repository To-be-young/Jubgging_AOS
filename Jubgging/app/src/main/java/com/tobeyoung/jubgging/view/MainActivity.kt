package com.tobeyoung.jubgging.view
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
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
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navi_host) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

//        NavigationUI.setupWithNavController(binding.bottomNavigationView,
//            findNavController(R.id.navi_host))
    }
}
