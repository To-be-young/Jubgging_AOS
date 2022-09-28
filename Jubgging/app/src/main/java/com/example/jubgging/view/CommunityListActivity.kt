package com.example.jubgging.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityCommunityGroupListBinding
import com.example.jubgging.viewmodel.CommunityViewModel

class CommunityListActivity:AppCompatActivity() {
    private lateinit var binding: ActivityCommunityGroupListBinding
    private val viewModel: CommunityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_group_list)
        binding.lifecycleOwner = this
        binding.communityVm = viewModel

        viewModel.getCommunityGroupList()


        binding.cglCreateCommunityBtn.setOnClickListener {
            //이동
            val intent = Intent(this, CommunityCreateActivity::class.java)
            startActivity(intent)
        }



    }
}