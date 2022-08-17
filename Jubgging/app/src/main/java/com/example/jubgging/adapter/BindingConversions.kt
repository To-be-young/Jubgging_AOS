package com.example.jubgging.adapter

import android.widget.Button
import androidx.databinding.BindingAdapter

object BindingConversions {
    @JvmStatic
    @BindingAdapter("setEmailText","setPwdText","setPwdChkText")
    fun setAccountBtnEnable(button: Button, userIdText: String, userPwdText:String, userPwdChkText:String){
        if(userIdText.isNotEmpty() && userPwdText.isNotEmpty() && userPwdChkText.isNotEmpty()){
            button.isEnabled = userPwdText == userPwdChkText
        }else{
            button.isEnabled = false
        }
    }
}