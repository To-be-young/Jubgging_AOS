package com.example.jubgging.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CleanhouseViewModel : ViewModel() {
    private var _isSwitchOn = MutableLiveData<Boolean>()
    val isSwitchOn: LiveData<Boolean>
        get() = _isSwitchOn

    private var _distance = MutableLiveData<Double>()
    val distance: LiveData<Double>
        get() = _distance

    private var _cleanHouseMarkerObserve = MutableLiveData<Pair<Boolean, Double>>()
    val cleanHouseMarkerObserve : LiveData<Pair<Boolean, Double>>
        get() = _cleanHouseMarkerObserve

    init {
        _isSwitchOn.value = false
        _distance.value = 0.0
        _cleanHouseMarkerObserve.value = Pair(false, 1.0)
    }

    fun updateDistance(inputDistance:Double){
        _distance.value = inputDistance
    }

    fun updateIsSwitchOn() {
        _isSwitchOn.value = !isSwitchOn.value!!
    }

    fun updateCleanHouseMarkerObserve(first_value : Boolean, second_value : Double){
        _cleanHouseMarkerObserve.value = Pair(first_value, second_value)
    }

}