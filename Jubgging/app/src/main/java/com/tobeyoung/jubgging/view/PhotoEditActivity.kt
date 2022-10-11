package com.tobeyoung.jubgging.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityPhotoEditBinding
import com.tobeyoung.jubgging.model.PloggingModel
import com.tobeyoung.jubgging.viewmodel.PhotoShareViewModel
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.PhotoEditorView

class PhotoEditActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPhotoEditBinding
    private val viewModel: PhotoShareViewModel by viewModels()
    private var color: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_edit)
        binding.photoShareVm = viewModel
        binding.lifecycleOwner = this
        val uriString = intent.getStringExtra("photoUri")

        val photoUri = Uri.parse(uriString)
        /* val speed = intent.getStringExtra("speed")
         val distance = intent.getDoubleExtra("distance", 0.0)
         val time = intent.getStringExtra("time")*/

        color = getColor(R.color.black)

        val speed = "01`00"
        val distance = "1.00"
        val time = "00:50"

        val photoEditorView = findViewById<PhotoEditorView>(R.id.pe_v)
        val pretendardFont = ResourcesCompat.getFont(this, R.font.pretendard_semibold)

        val photoEditor = PhotoEditor.Builder(this, photoEditorView)


        if (photoUri != null) {
            Glide.with(this).load(photoUri).into(binding.peV.source)
        }

        binding.peGetTimeBtn.setOnClickListener {
            photoEditor.build().addText(pretendardFont, time, color)
        }
        binding.peGetSpeedBtn.setOnClickListener {
            photoEditor.build().addText(pretendardFont, speed, color)
        }
        binding.peGetDistanceBtn.setOnClickListener {
            photoEditor.build().addText(pretendardFont, distance, color)
        }

        binding.peBlackBtn.setOnClickListener(this)
        binding.peWhiteBtn.setOnClickListener(this)
        binding.peRedBtn.setOnClickListener(this)
        binding.peGreenBtn.setOnClickListener(this)
        binding.peBlueBtn.setOnClickListener(this)
        binding.peYellowBtn.setOnClickListener(this)

        binding.editSaveBtn.setOnClickListener {
//            photoEditor.build().addEmoji("$time")

        }

    }

    private inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? =
        when {
            Build.VERSION.SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
        }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.peBlackBtn -> {color = getColor(R.color.black)}
            binding.peWhiteBtn -> {color = getColor(R.color.white)}
            binding.peRedBtn -> {color = getColor(R.color.red)}
            binding.peGreenBtn -> {color = getColor(R.color.green_blue)}
            binding.peBlueBtn -> {color = getColor(R.color.brownish_grey)}
            binding.peYellowBtn -> {color = getColor(R.color.marigold)}

        }
    }
}