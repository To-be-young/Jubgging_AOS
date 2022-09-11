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
    private val _emailAuthFlag = MutableLiveData<Boolean>()

    private val _emailOverlapFlag = MutableLiveData<Int>()

    private val _codeSentFlag = MutableLiveData<Boolean>()
    private val _passAuthFlag = MutableLiveData<Boolean>()
    private val _timeoutCount = MutableLiveData<Int>()
    private val _timeoutText = MutableLiveData<String>()

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

    // 기본값 -1, 중복이면 1, 중복아니면 0
    val emailOverlapFlag: LiveData<Int>
        get() = _emailOverlapFlag

    // 중복이면 true, 중복아니면 false
    val nicknameOverlapFlag: LiveData<Boolean>
        get() = _nicknameOverlapFlag

    init {
        _emailAuthFlag.value = false
        _codeSentFlag.value = false
        _passAuthFlag.value = false
        _nicknameOverlapFlag.value = false
        _nicknameOverlapFlag.value = true
        _timeoutCount.value = 60
        _timeoutText.value = "1:00"
        _emailOverlapFlag.value = -1
    }

    fun timerStart(setPhoneCodeNotice: (message:String,flag:Boolean) -> Unit) {
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
                    setPhoneCodeNotice("입력시간이 초과되었습니다. 인증 요청을 재시도 해주세요.",false)
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
    fun checkNickNameOverlap(nickname: String, setNicknameNotice: (message: String,flag:Boolean) -> Unit) {
        signUpRepository.checkNicknameOverlap(nickname).subscribeBy(
            onSuccess = {
                _nicknameOverlapFlag.value = it.data!!
                if (it.data) {
                    //중복 O
                    setNicknameNotice("이미 사용중인 닉네임입니다.",false)
                } else {
                    //중복 X
                    setNicknameNotice("사용가능한 닉네임입니다.",true)
                }
            },
            onError = { it.printStackTrace() }
        )
    }

    //전화번호 발송 코드
    fun sendSMSCode(
        phoneNumber: String,
        activity: SignUpAuthActivity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
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
        setPhoneCodeNotice:(message: String,flag:Boolean)-> Unit,
    ) {
        auth = Firebase.auth

        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    //인증성공
                    updatePassAuthFlag(true)
                    setPhoneCodeNotice("인증에 성공하였습니다.",true)
                    timerStop()
                } else {
                    //인증실패
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        //인증실패 - 잘못된 인증번호 입력
                        updatePassAuthFlag(false)
                        timerStop()
                        setPhoneCodeNotice("인증번호가 틀렸습니다. 다시 입력해주세요",false)
                    } else if (task.exception is FirebaseTooManyRequestsException) {
                        //인증실패 - 너무 많은 수신 요청
                        updatePassAuthFlag(false)
                        timerStop()
                        setPhoneCodeNotice("동일한 기기에서 너무 많은 요청이 수신되었습니다. 나중에 다시 시도하세요.",false)
                    }
                }
            }
    }

    @SuppressLint("CheckResult")
    fun signUp(
        signUpRequest: SignUpRequest,
        showDialog:() -> Unit
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

    @SuppressLint("CheckResult")
    fun login(loginRequest: LoginRequest, moveToMain: () -> Unit, showToast: (tag: Int) -> Unit) {
        signUpRepository.login(loginRequest).subscribeBy(onSuccess = {
            if (it.code == 0) {
                moveToMain().apply {
                    showToast(0)
                }
            } else {
                showToast(1)
            }
        }, onError = {
            it.printStackTrace()
        })
    }

    @SuppressLint("CheckResult")
    fun checkEmailOverlap(email: String, setEmailOverlapNotice: (flag:Int) -> Unit) {
        signUpRepository.checkEmailOverlap(email).subscribeBy(
            onSuccess = {
                if (it.code == 0) {
                    if (it.data) {
                        //중복일 떄
                        _emailOverlapFlag.value = 1
                        setEmailOverlapNotice(1)
                    } else {
                        //중복이 아닐 때
                        _emailOverlapFlag.value = 0
                        setEmailOverlapNotice(0)
                    }
                }
            }, onError = {
                it.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    fun sendEmailCode(emailRequest:EmailRequest, showToast: (message:String) -> Unit) {
        signUpRepository.sendEmailCode(emailRequest).subscribeBy(
            onSuccess = {
                if (it.code == 0) {
                    showToast("입력하신 이메일로 인증번호가 발송되었습니다.")
                }
            }, onError = {
                it.printStackTrace()
            })
    }
    @SuppressLint("CheckResult")
    fun verifyEmailCode(emailCodeAuthRequest: EmailCodeAuthRequest,setVerifyNotice:(msg:String,flag:Boolean)-> Unit,setUIEnable:(flag:Boolean)->Unit) {
        signUpRepository.verifyEmailCode(emailCodeAuthRequest).subscribeBy(
            onSuccess = {
                if (it.code == 0) {
                    setVerifyNotice("인증에 성공하였습니다.",true)
                    setUIEnable(false)
                    updateEmailAuthFlag(true)
                }else{
                    setVerifyNotice(it.errorMessage().toString(),false)
                }
            }, onError = {
                it.printStackTrace()
            })
    }
}