package com.tobeyoung.jubgging.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tobeyoung.jubgging.databinding.ActivityMyCommunityBinding
import com.tobeyoung.jubgging.viewmodel.CommunityViewModel

class MyCommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyCommunityBinding
    private val viewModel: CommunityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding.lifecycleOwner = this
        binding.userVm = viewModel

    }
}