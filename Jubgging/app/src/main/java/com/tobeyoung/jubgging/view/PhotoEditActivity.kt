package com.tobeyoung.jubgging.view

import android.graphics.Typeface
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityPhotoEditBinding
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

        val photoEditorView = findViewById<PhotoEditorView>(R.id.pe_v)
        val pretendardFont = ResourcesCompat.getFont(this, R.font.pretendard_semibold)

        val photoEditor = PhotoEditor.Builder(this, photoEditorView)

        photoEditorView.source.setImageResource(R.drawable.temp_edit_image)

        //기록 이모지화
        
        //텍스트 추가 가능


        binding.emojiBtn.setOnClickListener {
            photoEditor.build().addText(pretendardFont,"km", getColor(R.color.teal))
        }







    }
}