package com.tobeyoung.jubgging.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.adapter.MyCommunityViewPagerAdapter
import com.tobeyoung.jubgging.databinding.ActivityMyCommunityBinding
import com.tobeyoung.jubgging.viewmodel.CommunityViewModel

class MyCommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyCommunityBinding
    private val viewModel: CommunityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_community)
        binding.lifecycleOwner = this
        binding.communityVm = viewModel

        val viewPagerAdapter = MyCommunityViewPagerAdapter(this)
        binding.myCommunityVp.adapter = viewPagerAdapter

    }
}