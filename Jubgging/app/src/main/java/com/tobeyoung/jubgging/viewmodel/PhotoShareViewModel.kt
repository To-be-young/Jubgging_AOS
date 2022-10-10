package com.tobeyoung.jubgging.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PhotoShareViewModel : ViewModel() {

    private var _isImageCropped = MutableLiveData<Boolean>()
    val isImageCropped: LiveData<Boolean>
        get() = _isImageCropped


    init {
        _isImageCropped.value = false
    }
    fun updateIsImageCropped(flag:Boolean){
        _isImageCropped.value = flag
    }

}