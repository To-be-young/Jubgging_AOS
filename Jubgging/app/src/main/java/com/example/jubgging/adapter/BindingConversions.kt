package com.example.jubgging.adapter

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import com.example.jubgging.R

object BindingConversions {
    @JvmStatic
    @BindingAdapter("setOnTextView", "setOffTextView")
    fun setSwitchText(
        switch: SwitchCompat,
        onTextView: TextView,
        offTextView: TextView,
    ) {
        onTextView.gravity = Gravity.CENTER_VERTICAL
        offTextView.gravity = Gravity.CENTER_VERTICAL
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                onTextView.setText(R.string.off)
                onTextView.visibility = View.VISIBLE
                offTextView.visibility = View.GONE
            } else {
                offTextView.setText(R.string.on)
                offTextView.visibility = View.VISIBLE
                onTextView.visibility = View.GONE
            }
        }
    }
}