package com.example.jubgging.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityCommunityGroupDetailBinding
import com.example.jubgging.viewmodel.CommunityViewModel

class CommunityDetailActivity:AppCompatActivity() {
    private lateinit var binding: ActivityCommunityGroupDetailBinding
    private val viewModel: CommunityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_group_detail)
        binding.lifecycleOwner = this
        binding.communityVm = viewModel

        val postId = intent.getIntExtra("postId",0)
        viewModel.getCommunityDetail(postId)
        //시간 자르기

        binding.cgdJoinBtn.setOnClickListener {
            val intent = Intent(this,CommunityJoinActivity::class.java)
            startActivity(intent)
        }

    }
}