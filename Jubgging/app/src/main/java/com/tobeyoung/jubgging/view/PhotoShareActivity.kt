package com.tobeyoung.jubgging.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityPhotoShareBinding
import com.tobeyoung.jubgging.viewmodel.PhotoShareViewModel

class PhotoShareActivity:AppCompatActivity() {
    private lateinit var binding: ActivityPhotoShareBinding
    private val viewModel:PhotoShareViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_share)
        binding.photoShareVm = viewModel
        binding.lifecycleOwner = this





        //사진 가져오기
        binding.psPhotoIv.setOnLongClickListener {
            Toast.makeText(this,"long clicked",Toast.LENGTH_SHORT).show()
            true
        }

        //사진 편집하기
        binding.psEditPhotoCl.setOnClickListener {
            val intent = Intent(this,PhotoEditActivity::class.java)
            startActivity(intent)
        }

        //instagram story share
        binding.psInstagramStoryShareBtn.setOnClickListener {

        }
    }
}