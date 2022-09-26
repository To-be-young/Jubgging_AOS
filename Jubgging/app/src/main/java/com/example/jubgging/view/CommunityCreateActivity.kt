package com.example.jubgging.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityCommunityGroupCreateBinding
import com.example.jubgging.viewmodel.CommunityViewModel

class CommunityCreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityGroupCreateBinding
    private val viewModel: CommunityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_group_create)
        binding.lifecycleOwner = this
        binding.communityVm = viewModel
    }

}