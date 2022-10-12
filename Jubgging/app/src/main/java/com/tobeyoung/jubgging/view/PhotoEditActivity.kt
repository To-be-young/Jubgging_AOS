package com.tobeyoung.jubgging.view

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityPhotoEditBinding
import com.tobeyoung.jubgging.viewmodel.PhotoShareViewModel
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.PhotoEditorView


class PhotoEditActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPhotoEditBinding
    private val viewModel: PhotoShareViewModel by viewModels()
    private var colorCode: String = "#FF000000"
    private var color: Int = 0
    private var logo: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_edit)
        binding.photoShareVm = viewModel
        binding.lifecycleOwner = this
        val uriString = intent.getStringExtra("photoUri")

        val photoUri = Uri.parse(uriString)
        val speed = intent.getStringExtra("speed")
        val distance = intent.getStringExtra("distance")
        val time = intent.getStringExtra("time")

        color = ResourcesCompat.getColor(this.resources, R.color.black, theme)


        val photoEditorView = findViewById<PhotoEditorView>(R.id.pe_v)

        val pretendardFont = ResourcesCompat.getFont(this, R.font.pretendard_semibold)

        val photoEditor = PhotoEditor.Builder(this, photoEditorView)


        if (photoUri != null) {
            Glide.with(this).load(photoUri).into(binding.peV.source)
        }
        photoEditor.build()
            .addImage(Bitmap.createBitmap(BitmapFactory.decodeResource(this.resources,
                R.drawable.ic_app_title_black)))


        binding.peGetSpeedBtn.setOnClickListener {
            photoEditor.build().addText(pretendardFont, speed, color)
            //icon 추가
            photoEditor.build().addImage(getBitmapFromVectorDrawable(this,
                R.drawable.ic_baseline_speed_24,
                colorCode))

        }
        binding.peGetDistanceBtn.setOnClickListener {
            photoEditor.build().addText(pretendardFont, "$distance Km", color)
            //icon 추가
            photoEditor.build().addImage(getBitmapFromVectorDrawable(this,
                R.drawable.ic_baseline_electric_bolt_24,
                colorCode))

        }
        binding.peGetTimeBtn.setOnClickListener {
            photoEditor.build().addText(pretendardFont, time, color)
            //icon 추가
            photoEditor.build().addImage(getBitmapFromVectorDrawable(this,
                R.drawable.ic_baseline_access_time_24,
                colorCode))
        }

        binding.peLogoBlackBtn.setOnClickListener {
            logo = Bitmap.createBitmap(BitmapFactory.decodeResource(this.resources,
                R.drawable.ic_app_title_black))
            photoEditor.build().addImage(logo)
        }
        binding.peLogoWhiteBtn.setOnClickListener {
            logo = Bitmap.createBitmap(BitmapFactory.decodeResource(this.resources,
                R.drawable.ic_app_title_white))
            photoEditor.build().addImage(logo)
        }



        binding.peBlackBtn.setOnClickListener(this)
        binding.peWhiteBtn.setOnClickListener(this)
        binding.peRedBtn.setOnClickListener(this)
        binding.peGreenBtn.setOnClickListener(this)
        binding.peBlueBtn.setOnClickListener(this)
        binding.peYellowBtn.setOnClickListener(this)


        binding.editSaveBtn.setOnClickListener {

        }

    }

    private inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? =
        when {
            Build.VERSION.SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
        }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.peBlackBtn -> {
                colorCode = "#FF000000"
                color = ResourcesCompat.getColor(this.resources, R.color.black, theme)
            }
            binding.peWhiteBtn -> {
                colorCode = "#FFFFFFFF"
                color = ResourcesCompat.getColor(this.resources, R.color.white, theme)
            }
            binding.peRedBtn -> {
                colorCode = "#FF3939"
                color = ResourcesCompat.getColor(this.resources, R.color.orangey_red, theme)
            }
            binding.peGreenBtn -> {
                colorCode = "#00B786"
                color = ResourcesCompat.getColor(this.resources, R.color.green_blue, theme)

            }
            binding.peBlueBtn -> {
                colorCode = "#335AFF"
                color = ResourcesCompat.getColor(this.resources, R.color.lightish_blue, theme)

            }
            binding.peYellowBtn -> {
                colorCode = "#FFC400"
                color = ResourcesCompat.getColor(this.resources, R.color.marigold, theme)

            }
        }
    }

    private fun getBitmapFromVectorDrawable(
        context: Context,
        drawableId: Int,
        colorCode: String,
    ): Bitmap? {
        val drawable = ContextCompat.getDrawable(context, drawableId)
        drawable?.setTintList(ColorStateList.valueOf(Color.parseColor(colorCode)))
        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}