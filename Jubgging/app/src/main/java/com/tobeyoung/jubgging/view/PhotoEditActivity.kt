package com.tobeyoung.jubgging.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityPhotoEditBinding
import com.tobeyoung.jubgging.model.PloggingModel
import com.tobeyoung.jubgging.viewmodel.PhotoShareViewModel
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.PhotoEditorView
import ja.burhanrashid52.photoeditor.shape.ShapeBuilder


class PhotoEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoEditBinding
    private val viewModel: PhotoShareViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_edit)
        binding.photoShareVm = viewModel
        binding.lifecycleOwner = this
        val uriString = intent.getStringExtra("photoUri")
        val photoUri = Uri.parse(uriString)

        val photoEditorView = findViewById<PhotoEditorView>(R.id.pe_v)
        val pretendardFont = ResourcesCompat.getFont(this, R.font.pretendard_semibold)

        val photoEditor = PhotoEditor.Builder(this, photoEditorView)

        val speed = intent.getStringExtra("speed")
        val distance = intent.getDoubleExtra("distance",0.0)
        val time = intent.getIntExtra("time",0)
        val pathway = intent.parcelableArrayList<PloggingModel>("pathway")

        if(photoUri != null){
            Glide.with(this).load(photoUri).into(binding.peV.source)
        }

        //기록 이모지화

 /*       val bitmap:Bitmap = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888)
        val canvas:Canvas = Canvas(bitmap)
        var path = Path()
        if(pathway != null){
            for(i in 0..pathway.size){
                path.lineTo(pathway[i].latitude.toFloat()/100,pathway[i].longitude.toFloat())
            }
        }
        val paint = Paint()
        paint.strokeWidth = 5f
        paint.style = Paint.Style.FILL
        paint.color = getColor(R.color.teal)

        canvas.drawPath(path,paint)*/


        //텍스트 추가 가능


        binding.emojiBtn.setOnClickListener {
            photoEditor.build().addText(pretendardFont, "km", getColor(R.color.teal))
        }

    }
    private inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
    }
}