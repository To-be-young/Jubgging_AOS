package com.example.jubgging.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CleanhouseViewModel : ViewModel(){
    private var _liveFlag = MutableLiveData<Boolean>()
    val liveFlag : LiveData<Boolean>
        get() = _liveFlag

    init {
        _liveFlag.value = false
    }

    fun updateLiveFlag(){
        _liveFlag.value = !liveFlag.value!!
    }
}