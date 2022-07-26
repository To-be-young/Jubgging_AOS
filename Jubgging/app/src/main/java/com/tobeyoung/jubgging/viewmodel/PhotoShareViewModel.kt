package com.tobeyoung.jubgging.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tobeyoung.jubgging.R

class PhotoShareViewModel : ViewModel() {
    private val _image = MutableLiveData<Int>()
    val image: LiveData<Int>
        get() =_image

    private val _background = MutableLiveData<Int>()
    val background: LiveData<Int>
        get() = _background

    private val _content = MutableLiveData<String>()
    val content: LiveData<String>
        get() = _content

//    //이미지, 배경 리스트들
//    private var imageList = mutableListOf<Int>()
//    private var backgroundList = mutableListOf<Int>()

    init{
        _image.value = R.drawable.temp_edit_image
        _background.value = R.color.white
        _content.value = ""
    }

}