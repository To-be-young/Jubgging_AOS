package com.example.jubgging.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jubgging.R

class InstagramShareViewModel : ViewModel() {
    //뷰에 나타낼 값들. 라이브데이터 형식
    var image = MutableLiveData<Int>()
    //var _image = MutableLiveData<Int>()
//image//liveData<int>()

    var background = MutableLiveData<Int>()
    val content = MutableLiveData<String>()

//    //이미지, 배경 리스트들
//    private var imageList = mutableListOf<Int>()
//    private var backgroundList = mutableListOf<Int>()

    init{
        image.value = R.drawable.android

        background.value = R.color.white

        content.value = ""


    }

}