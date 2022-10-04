package com.example.jubgging.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityEtcOssBinding
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader

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