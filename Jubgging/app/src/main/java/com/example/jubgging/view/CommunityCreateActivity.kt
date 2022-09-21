package com.example.jubgging.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityCommunityCreateBinding
import com.example.jubgging.viewmodel.CommunityViewModel

class CommunityCreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityCreateBinding
    private val viewModel: CommunityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_create)
        binding.lifecycleOwner = this
        binding.communityVm = viewModel
    }

}