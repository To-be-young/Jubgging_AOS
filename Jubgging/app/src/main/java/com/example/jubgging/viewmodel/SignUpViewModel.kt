package com.example.jubgging.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jubgging.network.data.request.LoginRequest
import com.example.jubgging.network.data.request.SignUpRequest
import com.example.jubgging.repository.SignUpRepositoryImpl
import com.example.jubgging.view.SignUpAuthActivity
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.rxkotlin.subscribeBy
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

    private val _userInfo = MutableLiveData<SignUpRequest>()

    private val _nicknameOverlapFlag = MutableLiveData<Boolean>()

    private lateinit var timerJob: Job

    val emailAuthFlag: LiveData<Boolean>
        get() = _emailAuthFlag

    val codeSentFlag: LiveData<Boolean>
        get() = _codeSentFlag

    val passAuthFlag: LiveData<Boolean>
        get() = _passAuthFlag

    val timeoutText: LiveData<String>
        get() = _timeoutText

    val userInfo: LiveData<SignUpRequest>
        get() = _userInfo

    // 중복이면 true, 중복아니면 false
    val nicknameOverlapFlag: LiveData<Boolean>
        get() = _nicknameOverlapFlag

    init {
        _emailAuthFlag.value = false
        _codeSentFlag.value = false
        _passAuthFlag.value = false
        _nicknameOverlapFlag.value = false
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
                if (_timeoutCount.value!! < 10) {
                    _timeoutText.value =
                        "${_timeoutCount.value!! / 100}:0${_timeoutCount.value!! % 100}"
                }
                delay(1000L)
                if (_timeoutCount.value!! == 0) {
                    updateCodeSentFlag(false)
                    _timeoutText.value = "0:00"
                    showToast(4)
                }
            }
        }
    }

    private fun timerStop() {
        if (::timerJob.isInitialized) timerJob.cancel()
        _timeoutText.value = "0:00"
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

    @SuppressLint("CheckResult")
    fun checkNickNameOverlap(nickname: String,showToast: (tag: Int) -> Unit) {
        signUpRepository.checkNicknameOverlap(nickname).subscribeBy(
            onSuccess = {
                _nicknameOverlapFlag.value = it.data!!
                if(it.data){
                    //중복 O
                    showToast(6)
                }else{
                    //중복 X
                    showToast(7)
                }
            },
            onError = { it.printStackTrace() }
        )
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

    @SuppressLint("CheckResult")
    fun signUp(
        signUpRequest: SignUpRequest,
        moveToLogin: () -> Unit,
        showToast: (tag: Int) -> Unit,
    ) {
        signUpRepository.signUp(signUpRequest).subscribeBy(
            onSuccess = {
                if (it.code == 0) {
                    moveToLogin().apply {
                        showToast(5)
                    }
                }else{
                   //회원가입 실패 시 예외처리 필요
                }
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    @SuppressLint("CheckResult")
    fun login(loginRequest: LoginRequest, moveToMain: () -> Unit, showToast: (tag: Int) -> Unit) {
        signUpRepository.login(loginRequest).subscribeBy(onSuccess = {
            if (it.code == 0) {
                moveToMain().apply {
                    showToast(0)
                }
            }else{
                showToast(1)
            }
        }, onError = {
            it.printStackTrace()
        })
    }
}