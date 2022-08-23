package com.example.jubgging.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jubgging.repository.SignUpRepositoryImpl
import com.example.jubgging.view.SignUpAuthActivity
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SignUpViewModel : ViewModel() {
    private val signUpRepository = SignUpRepositoryImpl()
    private lateinit var auth: FirebaseAuth
    private val _emailAuthFlag = MutableLiveData<Boolean>()
    private val _codeSentFlag = MutableLiveData<Boolean>()
    private val _passAuthFlag = MutableLiveData<Boolean>()
    private val _timeoutCount = MutableLiveData<Int>()
    private val _timeoutText = MutableLiveData<String>()
    private lateinit var timerJob: Job


    val emailAuthFlag: LiveData<Boolean>
        get() = _emailAuthFlag

    val codeSentFlag: LiveData<Boolean>
        get() = _codeSentFlag

    val passAuthFlag: LiveData<Boolean>
        get() = _passAuthFlag

    val timeoutText: LiveData<String>
        get() = _timeoutText


    init {
        _emailAuthFlag.value = false
        _codeSentFlag.value = false
        _passAuthFlag.value = false
        _timeoutCount.value = 60
        _timeoutText.value = "1:00"
    }

    fun timerStart(showToast: (tag: Int) -> Unit) {
        if (::timerJob.isInitialized) timerJob.cancel()
        _timeoutCount.value = 60
        _timeoutText.value = "1:00"
        timerJob = viewModelScope.launch {
            while (_timeoutCount.value!! > 0) {
                _timeoutCount.value = _timeoutCount.value!!.minus(1)
                _timeoutText.value = "${_timeoutCount.value!! / 100}:${_timeoutCount.value!! % 100}"
                if(_timeoutCount.value!! <10){
                    _timeoutText.value = "${_timeoutCount.value!! / 100}:0${_timeoutCount.value!! % 100}"
                }
                delay(1000L)
                if (_timeoutCount.value!! == 0) {
                    updateCodeSentFlag(false)
                    _timeoutText.value = "00:00"
                    showToast(4)
                }
            }
        }
    }

    private fun timerStop() {
        if (::timerJob.isInitialized) timerJob.cancel()
        _timeoutText.value = "00:00"
    }

    fun updateEmailAuthFlag(flag: Boolean) {
        _emailAuthFlag.value = flag
    }

    fun updateCodeSentFlag(flag: Boolean) {
        _codeSentFlag.value = flag
    }

    private fun updatePassAuthFlag(flag: Boolean) {
        _passAuthFlag.value = flag
    }


    //전화번호 발송 코드
    fun sendSMSCode(
        phoneNumber: String,
        activity: SignUpAuthActivity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
    ) {
        auth = Firebase.auth
        //setPhoneProviderOption
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        auth.setLanguageCode("kr")
        //실제 발송 코드
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    //options
    fun verifySMSCode(
        credential: PhoneAuthCredential,
        activity: SignUpAuthActivity,
        showToast: (tag: Int) -> Unit,
    ) {
        auth = Firebase.auth

        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    //인증성공
                    updatePassAuthFlag(true)
                    showToast(1)
                    timerStop()
                } else {
                    //인증실패
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        //인증실패 - 잘못된 인증번호 입력
                        updatePassAuthFlag(false)
                        timerStop()
                        showToast(2)
                    } else if (task.exception is FirebaseTooManyRequestsException) {
                        //인증실패 - 너무 많은 수신 요청
                        updatePassAuthFlag(false)
                        timerStop()
                        showToast(3)
                    }
                }
            }
    }
}