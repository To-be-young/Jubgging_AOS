package com.tobeyoung.jubgging.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityEtcOssBinding

class EtcOssActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEtcOssBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEtcOssBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.etcOssNoticeTv.text = ReadTextFile()
    }

    private fun ReadTextFile() : String{

        val inn = resources.openRawResource(R.raw.etc_oss)
        var b : ByteArray = inn.readBytes()
        val fi = String(b)

        return fi
    }
}

