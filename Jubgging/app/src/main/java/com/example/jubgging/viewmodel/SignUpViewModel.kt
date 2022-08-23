package com.example.jubgging.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jubgging.repository.SignUpRepositoryImpl
import com.example.jubgging.view.SignUpAuthActivity
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class SignUpViewModel : ViewModel() {
    private val signUpRepository = SignUpRepositoryImpl()
    private lateinit var auth: FirebaseAuth
    private val _emailAuthFlag = MutableLiveData<Boolean>()
    private val _codeSentFlag = MutableLiveData<Boolean>()
    private val _passAuthFlag = MutableLiveData<Boolean>()

    val emailAuthFlag: LiveData<Boolean>
        get() = _emailAuthFlag

    val codeSentFlag: LiveData<Boolean>
        get() = _codeSentFlag

    val passAuthFlag: LiveData<Boolean>
        get() = _passAuthFlag

    init {
        _emailAuthFlag.value = false
        _codeSentFlag.value = false
        _passAuthFlag.value = false
    }


    fun updateEmailAuthFlag(flag: Boolean) {
        _emailAuthFlag.value = flag
    }

    fun updateCodeSentFlag(flag: Boolean) {
        _codeSentFlag.value = flag
    }

    fun updatePassAuthFlag(flag: Boolean) {
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
                } else {
                    //인증실패
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        //인증실패 - 잘못된 인증번호 입력
                        updatePassAuthFlag(false)
                        showToast(2)
                    } else if (task.exception is FirebaseTooManyRequestsException) {
                        //인증실패 - 너무 많은 수신 요청
                        updatePassAuthFlag(false)
                        showToast(3)
                    }
                }
            }
    }
}