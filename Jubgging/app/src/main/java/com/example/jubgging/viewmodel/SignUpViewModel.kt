package com.example.jubgging.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jubgging.network.data.request.EmailCodeAuthRequest
import com.example.jubgging.network.data.request.EmailRequest
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
    val overlapType:Pair<String,String> = Pair("email","nickname")

    companion object {
        var accessToken: String = ""
    }



    // 중복이면 1, 통과하면 0, default -1
    private val _overlapFlag = MutableLiveData<Int>()
    val overlapFlag:LiveData<Int>
        get() = _overlapFlag

    //인증코드 발송 여부 flag
    private val _codeSentFlag = MutableLiveData<Boolean>()
    val codeSentFlag: LiveData<Boolean>
        get() = _codeSentFlag

    //인증 코드 timeout 여부 flag
    private val _timeoutFlag = MutableLiveData<Boolean>()
    val timeoutFlag: LiveData<Boolean>
        get() = _timeoutFlag


    //인증 성공 여부 Flag
    // 실패하면 1, 통과하면 0, default -1
    private val _passAuthFlag = MutableLiveData<Int>()
    val passAuthFlag: LiveData<Int>
        get() = _passAuthFlag

    private val _pTimeoutCount = MutableLiveData<Int>()

    private val _eTimeoutCount = MutableLiveData<Int>()

    private val _pTimeoutText = MutableLiveData<String>()
    val pTimeoutText: LiveData<String>
        get() = _pTimeoutText

    private val _eTimeoutText = MutableLiveData<String>()
    val eTimeoutText: LiveData<String>
        get() = _eTimeoutText

    private lateinit var timerJob: Job

    init {
        _codeSentFlag.value = false
        _timeoutFlag.value = false
        _passAuthFlag.value = -1
        _overlapFlag.value = -1
        _pTimeoutCount.value = 60
        _eTimeoutCount.value = 180

        _pTimeoutText.value = "1:00"
        _eTimeoutText.value = "3:00"
    }


    private fun pTimerStop() {
        if (::timerJob.isInitialized) timerJob.cancel()
        _pTimeoutText.value = "0:00"
    }

    private fun eTimerStop() {
        if (::timerJob.isInitialized) timerJob.cancel()
        _eTimeoutText.value = "0:00"
    }

    fun updateCodeSentFlag(flag: Boolean) {
        _codeSentFlag.value = flag
    }

    private fun updateTimeoutFlag(flag: Boolean) {
        _timeoutFlag.value = flag
    }

    private fun updatePassAuthFlag(flag: Int) {
        _passAuthFlag.value = flag
    }
    private fun updateOverlapFlag(flag: Int){
        _overlapFlag.value = flag
    }


    //email overlap check Api
    @SuppressLint("CheckResult")
    fun checkEmailOverlap(email: String) {
        signUpRepository.checkEmailOverlap(email).subscribeBy(
            onSuccess = {
                if (it.code == 0) {
                    if (it.data) {
                        //중복일 떄
                        updateOverlapFlag(1)
                    } else {
                        //중복이 아닐 때
                        updateOverlapFlag(0)
                    }
                }
            }, onError = {
                it.printStackTrace()
            })
    }

    //email 인증 code 발송 Api
    @SuppressLint("CheckResult")
    fun sendEmailCode(emailRequest: EmailRequest) {
        if (!codeSentFlag.value!!) {
            //인증코드 최초 발송
            signUpRepository.sendEmailCode(emailRequest).subscribeBy(
                onSuccess = {
                    if (it.code == 0) {
                        updateCodeSentFlag(true)
                        //TimerStart
                        emailCodeTimerStart()
                        //발송 안내 Toast
//                        showToast("입력하신 이메일로 인증번호가 발송되었습니다.")
                    } else {
                        updateCodeSentFlag(false)
                    }
                }, onError = {
                    it.printStackTrace()
                })
        } else {
            //인증코드 재발송
            updateCodeSentFlag(false)
            updateTimeoutFlag(false)

            signUpRepository.reSendEmailCode(emailRequest).subscribeBy(
                onSuccess = {
                    if (it.code == 0) {
                        updateCodeSentFlag(true)

                        emailCodeTimerStart()
                    } else {
                        updateCodeSentFlag(false)
                    }
                }, onError = {
                    it.printStackTrace()
                })
        }
    }

    //email code 검사 Api
    @SuppressLint("CheckResult")
    fun verifyEmailCode(
        emailCodeAuthRequest: EmailCodeAuthRequest,
    ) {
        signUpRepository.verifyEmailCode(emailCodeAuthRequest).subscribeBy(
            onSuccess = {
                if (it.code == 0) {
                    eTimerStop()
                    updatePassAuthFlag(0)
                } else {
                    updatePassAuthFlag(1)
                }
            }, onError = {
                it.printStackTrace()
            })
    }


    //이메일 인증코드 3분 Timer
    private fun emailCodeTimerStart() {
        if (::timerJob.isInitialized) timerJob.cancel()
        _eTimeoutCount.value = 180
        _eTimeoutText.value = "3:00"
        timerJob = viewModelScope.launch {
            while (_eTimeoutCount.value!! > 0) {
                _eTimeoutCount.value = _eTimeoutCount.value!!.minus(1)
                _eTimeoutText.value =
                    "${_eTimeoutCount.value!! / 60}:${_eTimeoutCount.value!! % 60}"
                if (_eTimeoutCount.value!! in 120..130) {
                    _eTimeoutText.value =
                        "${_eTimeoutCount.value!! / 60}:0${_eTimeoutCount.value!! % 60}"
                }
                if (_eTimeoutCount.value!! in 60..70) {
                    _eTimeoutText.value =
                        "${_eTimeoutCount.value!! / 60}:0${_eTimeoutCount.value!! % 60}"
                }
                if (_eTimeoutCount.value!! < 10) {
                    _eTimeoutText.value =
                        "${_eTimeoutCount.value!! / 60}:0${_eTimeoutCount.value!! % 60}"
                }
                delay(1000L)
                if (_eTimeoutCount.value!! == 0) {
                    updateTimeoutFlag(true)
                    _eTimeoutText.value = "0:00"

                }
            }
        }
    }

    fun phoneCodeTimerStart() {
        if (::timerJob.isInitialized) timerJob.cancel()
        _pTimeoutCount.value = 60
        _pTimeoutText.value = "1:00"
        timerJob = viewModelScope.launch {
            while (_pTimeoutCount.value!! > 0) {
                _pTimeoutCount.value = _pTimeoutCount.value!!.minus(1)
                _pTimeoutText.value =
                    "${_pTimeoutCount.value!! / 100}:${_pTimeoutCount.value!! % 100}"
                if (_pTimeoutCount.value!! < 10) {
                    _pTimeoutText.value =
                        "${_pTimeoutCount.value!! / 100}:0${_pTimeoutCount.value!! % 100}"
                }
                delay(1000L)
                if (_pTimeoutCount.value!! == 0) {
                    updateTimeoutFlag(true)
                    _pTimeoutText.value = "0:00"
                }
            }
        }
    }

    //nickName overlap check Api
    @SuppressLint("CheckResult")
    fun checkNickNameOverlap(nickname: String) {
        signUpRepository.checkNicknameOverlap(nickname).subscribeBy(
            onSuccess = {
                if (it.data) {
                    //중복 O
                    updateOverlapFlag(1)
                } else {
                    //중복 X
                    updateOverlapFlag(0)
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
    ) {
        auth = Firebase.auth

        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    //인증성공
                    updatePassAuthFlag(0)
                    pTimerStop()
                } else {
                    //인증실패
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        //인증실패 - 잘못된 인증번호 입력
                        updatePassAuthFlag(1)
                    } else if (task.exception is FirebaseTooManyRequestsException) {
                        //인증실패 - 너무 많은 수신 요청
                        updatePassAuthFlag(2)
                    }
                }
            }
    }

    @SuppressLint("CheckResult")
    fun signUp(
        signUpRequest: SignUpRequest,
        showDialog: () -> Unit,
    ) {
        signUpRepository.signUp(signUpRequest).subscribeBy(
            onSuccess = {
                if (it.success) {
                    showDialog()
                } else {
                    //회원가입 실패 시 예외처리 필요
                    Log.d("TAG", "signUp: ${signUpRequest.userId}")
                    Log.d("TAG", "signUp:${it.msg} ")
                }
            },
            onError = {
                it.printStackTrace()
            }
        )
    }


    //login Api
    @SuppressLint("CheckResult")
    fun login(loginRequest: LoginRequest, moveToMain: () -> Unit, showToast: (tag: Int) -> Unit) {
        signUpRepository.login(loginRequest).subscribeBy(onSuccess = {
            if (it.code == 0) {
                moveToMain().apply {
                    showToast(0)
                    updateJwtToken(it.data.accessToken)
                }
            } else {
                showToast(1)
            }
        }, onError = {
            it.printStackTrace()
        })
    }
    private fun updateJwtToken(inputToken: String?) {
        if (inputToken != null) {
            accessToken = inputToken
        } else {
            accessToken = ""
        }

    }
}