package com.example.jubgging.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityCommunityGroupDetailBinding
import com.example.jubgging.databinding.ActivityCommunityGroupJoinBinding
import com.example.jubgging.viewmodel.CommunityViewModel

class CommunityJoinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityGroupJoinBinding
    private val viewModel: CommunityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_group_join)
        binding.lifecycleOwner = this
        binding.communityVm = viewModel
        val title = intent.getStringExtra("title").toString()
        val desc = intent.getStringExtra("desc").toString()
        val notice = intent.getStringExtra("notice").toString()
        val place = intent.getStringExtra("place").toString()
        val etc = intent.getStringExtra("etc").toString()
        val participant = intent.getIntExtra("participant",0)
        val capacity = intent.getIntExtra("capacity",0)

        val postId = intent.getIntExtra("postId",0)
        viewModel.getUserNicknameEmail()
        viewModel.updateDetailData(title,desc,notice,place,capacity,participant,etc)



        binding.cgjJoinBtn.setOnClickListener {

        }
    }
}