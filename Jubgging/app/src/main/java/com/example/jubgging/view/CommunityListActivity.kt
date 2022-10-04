package com.example.jubgging.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jubgging.R
import com.example.jubgging.adapter.CommunitiesPagingAdapter
import com.example.jubgging.databinding.ActivityCommunityGroupListBinding
import com.example.jubgging.viewmodel.CommunityViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CommunityListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityGroupListBinding
    private val viewModel: CommunityViewModel by viewModels()
    private val adapter = CommunitiesPagingAdapter()
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_group_list)
        binding.lifecycleOwner = this
        binding.communityVm = viewModel


        binding.communityGroupListRv.adapter = adapter
        binding.communityGroupListRv.layoutManager = LinearLayoutManager(this)

        startGetlist()

        binding.cglCreateCommunityBtn.setOnClickListener {
            //이동
            val intent = Intent(this, CommunityCreateActivity::class.java)
            startActivity(intent)
        }





    }
    private fun startGetlist() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getList().observe(this@CommunityListActivity) {
                adapter.submitData(this@CommunityListActivity.lifecycle, it)
            }
        }
    }
}