package com.tobeyoung.jubgging.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.to_be_young_jubgging.R
import com.to_be_young_jubgging.databinding.ActivityEtcOssBinding


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

