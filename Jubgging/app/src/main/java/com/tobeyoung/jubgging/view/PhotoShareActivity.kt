package com.tobeyoung.jubgging.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityPhotoShareBinding
import com.tobeyoung.jubgging.viewmodel.PhotoShareViewModel

class PhotoShareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoShareBinding
    private val viewModel: PhotoShareViewModel by viewModels()


    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            val uriContent = result.uriContent
            Glide.with(this).load(uriContent).into(binding.psPhotoIv)

        } else {
            // an error occurred
            val exception = result.error
            exception?.printStackTrace()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_share)
        binding.photoShareVm = viewModel
        binding.lifecycleOwner = this

        //기본 이미지 로드
        Glide.with(this).load(R.drawable.temp_edit_image).into(binding.psPhotoIv)

        //load & crop
        binding.psPhotoIv.setOnLongClickListener {
            startCrop()
            true
        }

        //사진 편집하기
        binding.psEditPhotoCl.setOnClickListener {
            val intent = Intent(this, PhotoEditActivity::class.java)
            startActivity(intent)
        }


        //instagram story share
        binding.psInstagramStoryShareBtn.setOnClickListener {

        }
    }

    private fun startCrop() {
        // start picker to get image for cropping and then use the image in cropping activity
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
                setAspectRatio(1,1)
            }
        )

    }
}