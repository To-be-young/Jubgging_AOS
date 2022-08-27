package com.example.jubgging.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

        initNavigation()

//        binding.bottomNavigationView.setOnItemSelectedListener(this)
//        supportFragmentManager.beginTransaction().add(R.id.main_fl, HomeFragment()).commit()

    }

    private fun initNavigation() {
        NavigationUI.setupWithNavController(binding.bottomNavigationView, findNavController(R.id.navi_host))
    }


//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        //추후 Jetpack의 NavigationUI로 수정할 예정
//        when (item.itemId) {
//            R.id.main_community -> {
//                supportFragmentManager.beginTransaction().replace(R.id.main_fl, CommunityFragment())
//                    .commitAllowingStateLoss()
//                return true
//            }
//            R.id.main_home -> {
//                Navigation.findNavController(binding.root).navigate(R.id.action_fragmentOne_to_fragmentTwo)
//                return true
//            }
//            R.id.main_my -> {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.main_fl, MyFragment()).commitAllowingStateLoss()
//                return true
//            }
//            else -> return false
//        }
//    }
}