package com.example.jubgging.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityCommunityGroupDetailBinding
import com.example.jubgging.databinding.ActivityCommunityGroupJoinBinding
import com.example.jubgging.viewmodel.CommunityViewModel

class CommunityJoinActivity:AppCompatActivity() {
    private lateinit var binding: ActivityCommunityGroupJoinBinding
    private val viewModel: CommunityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_group_join)
        binding.lifecycleOwner = this
        binding.communityVm = viewModel

        binding.cgjJoinBtn.setOnClickListener {
            
        }
    }
}