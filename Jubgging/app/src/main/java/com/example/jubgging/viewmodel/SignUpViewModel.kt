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
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val signUpRepository = SignUpRepositoryImpl()

    companion object {
        var accessToken: String = ""
    }


    // 중복이면 1, 통과하면 0, default -1
    private val _eOverlapFlag = MutableLiveData<Int>()
    val eOverlapFlag: LiveData<Int>
        get() = _eOverlapFlag

    private val _nOverlapFlag = MutableLiveData<Int>()
    val nOverlapFlag: LiveData<Int>
        get() = _nOverlapFlag

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
    private val _emailPassFlag = MutableLiveData<Int>()
    val emailPassFlag: LiveData<Int>
        get() = _emailPassFlag


    private val _eTimeoutCount = MutableLiveData<Int>()

    private val _eTimeoutText = MutableLiveData<String>()
    val eTimeoutText: LiveData<String>
        get() = _eTimeoutText

    private lateinit var timerJob: Job

    init {
        _codeSentFlag.value = false
        _timeoutFlag.value = false
        _emailPassFlag.value = -1
        _eOverlapFlag.value = -1
        _nOverlapFlag.value = -1
        _eTimeoutCount.value = 180
        _eTimeoutText.value = "3:00"
    }


    private fun eTimerStop() {
        if (::timerJob.isInitialized) timerJob.cancel()
        _eTimeoutText.value = "0:00"
    }

    private fun updateCodeSentFlag(flag: Boolean) {
        _codeSentFlag.value = flag
    }

    private fun updateTimeoutFlag(flag: Boolean) {
        _timeoutFlag.value = flag
    }

    private fun updateEmailPassFlag(flag: Int) {
        _emailPassFlag.value = flag
    }

    private fun updateEOverlapFlag(flag: Int) {
        _eOverlapFlag.value = flag
    }

    private fun updateNOverlapFlag(flag: Int) {
        _nOverlapFlag.value = flag
    }

    //email overlap check Api
    @SuppressLint("CheckResult")
    fun checkEmailOverlap(email: String) {
        signUpRepository.checkEmailOverlap(email).subscribeBy(
            onSuccess = {
                if (it.code == 0) {
                    if (it.data) {
                        //중복일 떄
                        updateEOverlapFlag(1)
                    } else {
                        //중복이 아닐 때
                        updateEOverlapFlag(0)
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
                    updateEmailPassFlag(0)
                } else {
                    updateEmailPassFlag(1)
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

    //nickName overlap check Api
    @SuppressLint("CheckResult")
    fun checkNickNameOverlap(nickname: String) {
        signUpRepository.checkNicknameOverlap(nickname).subscribeBy(
            onSuccess = {
                if (it.data) {
                    //중복 O
                    updateNOverlapFlag(1)
                } else {
                    //중복 X
                    updateNOverlapFlag(0)
                }
            },
            onError = { it.printStackTrace() }
        )
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
                updateJwtToken(it.data.accessToken)

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