package com.example.jubgging.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityCleanhouseMapBinding

class CleanHouseMapActivity:AppCompatActivity() {
    private lateinit var binding: ActivityCleanhouseMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cleanhouse_map)
        binding.lifecycleOwner =this
    }
}