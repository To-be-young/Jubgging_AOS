package com.tobeyoung.jubgging.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.to_be_young_jubgging.R
import com.to_be_young_jubgging.databinding.ActivityCommunityGroupDetailBinding
import com.tobeyoung.jubgging.viewmodel.CommunityViewModel

class CommunityDetailActivity:AppCompatActivity() {
    private lateinit var binding: ActivityCommunityGroupDetailBinding
    private val viewModel: CommunityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_group_detail)
        binding.lifecycleOwner = this
        binding.communityVm = viewModel

        val postId = intent.getIntExtra("postId",0).toInt()
        viewModel.getCommunityDetail(postId)
        //시간 자르기


        binding.cgdJoinBtn.setOnClickListener {
            val intent = Intent(this,CommunityJoinActivity::class.java)
            intent.putExtra("title",viewModel.communityTitle.value)
            intent.putExtra("desc",viewModel.communityDesc.value)
            intent.putExtra("notice0",viewModel.communityNotice0.value)
            intent.putExtra("notice1",viewModel.communityNotice1.value)
            intent.putExtra("notice2",viewModel.communityNotice2.value)
            intent.putExtra("place",viewModel.communityPlace.value)
            intent.putExtra("etc",viewModel.communityEtc.value)
            intent.putExtra("participant",viewModel.communityParticipant.value)
            intent.putExtra("capacity",viewModel.communityCapacity.value)
            intent.putExtra("nickname",viewModel.communityNickname.value)
            intent.putExtra("date",viewModel.communityDate.value)
            intent.putExtra("sTime",viewModel.communitySTime.value)
            intent.putExtra("eTime",viewModel.communityETime.value)



            intent.putExtra("postId",postId)

            startActivity(intent)
        }

    }
}