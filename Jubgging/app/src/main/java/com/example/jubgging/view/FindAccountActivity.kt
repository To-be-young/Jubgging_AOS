package com.example.jubgging.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.jubgging.FindIdFragment
import com.example.jubgging.FindPwFragment
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityFindAccountBinding
import com.google.android.material.tabs.TabLayout

class FindAccountActivity : AppCompatActivity() {

    lateinit var binding : ActivityFindAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFindAccountBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.imsi, FindIdFragment()).commit()
        binding.findAccountTl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        replaceView(FindIdFragment())
                    }
                    1 -> {
                        replaceView(FindPwFragment())
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    fun replaceView(tab : Fragment){
        var selectedFragment : Fragment? = null
        selectedFragment = tab
        selectedFragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.imsi, it).commit()
        }
    }
}

