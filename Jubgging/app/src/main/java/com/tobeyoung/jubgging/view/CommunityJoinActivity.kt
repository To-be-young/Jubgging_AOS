package com.tobeyoung.jubgging.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.to_be_young_jubgging.R
import com.to_be_young_jubgging.databinding.ActivityCommunityGroupJoinBinding

import com.tobeyoung.jubgging.viewmodel.CommunityViewModel

class CommunityJoinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityGroupJoinBinding
    private val viewModel: CommunityViewModel by viewModels()
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_group_join)
        binding.lifecycleOwner = this
        binding.communityVm = viewModel
        val title = intent.getStringExtra("title").toString()
        val desc = intent.getStringExtra("desc").toString()

        val notice0 = intent.getStringExtra("notice0").toString()
        val notice1 = intent.getStringExtra("notice1").toString()
        val notice2 = intent.getStringExtra("notice2").toString()

        val place = intent.getStringExtra("place").toString()
        val etc = intent.getStringExtra("etc").toString()
        val participant = intent.getIntExtra("participant",0)
        val capacity = intent.getIntExtra("capacity",0)
        val date = intent.getStringExtra("date").toString()
        val sTime = intent.getStringExtra("sTime").toString()
        val eTime = intent.getStringExtra("eTime").toString()
        val nickname = intent.getStringExtra("nickname").toString()

        val postId = intent.getIntExtra("postId",0).toInt()
        viewModel.getUserNicknameEmail()
        viewModel.updateDetailData(title,desc,notice0,notice1,notice2,place,capacity,participant,etc,date, sTime, eTime, nickname)
        viewModel.updatePostId(postId)

        binding.cgjToolBar.title = "${viewModel.communityTitle.value} 참여 신청"

        dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_finish_join)
            window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }

        viewModel.isSuccess.observe(this) {
            if (it) {
                showDialog()
            }
        }


        binding.cgjJoinBtn.setOnClickListener {
            viewModel.joinCommunity(viewModel.postId.value!!)
        }
    }
    private fun showDialog() {
        dialog.show()
        dialog.findViewById<Button>(R.id.dcgj_move_btn).setOnClickListener {
            //나중에 함께 줍깅으로 옮길 예정
            moveToCommunityList()
        }
    }
    private fun moveToCommunityList() {
        val intent = Intent(this, CommunityListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }
}