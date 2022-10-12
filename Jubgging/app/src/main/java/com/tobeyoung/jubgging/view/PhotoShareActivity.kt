package com.tobeyoung.jubgging.view

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
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
    private  var uri: Uri =  Uri.parse("android.resource://com.tobeyoung.jubgging/drawable/temp_edit_image")

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            val uriContent = result.uriContent
            if (uriContent != null) {
                Glide.with(this).load(uriContent).into(binding.psPhotoIv)
                uri = uriContent
            }

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

        val speed = intent.getStringExtra("speed")
        val distance = intent.getStringExtra("distance")
        val time = intent.getStringExtra("time")



        Log.d("TAG", "onCreate: $speed, $distance, $time")
        //load & crop
        binding.psPhotoIv.setOnLongClickListener {
            startCrop()
            true
        }

        //사진 편집하기
        binding.psEditPhotoCl.setOnClickListener {
            val intent = Intent(this, PhotoEditActivity::class.java)
            intent.putExtra("photoUri",uri.toString())
            intent.putExtra("speed",speed)
            intent.putExtra("distance",distance)
            intent.putExtra("time",time)
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


    private inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
        SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
    }
}