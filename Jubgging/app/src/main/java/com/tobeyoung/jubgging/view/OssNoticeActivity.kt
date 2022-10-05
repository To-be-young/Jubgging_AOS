package com.tobeyoung.jubgging.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.tobeyoung.jubgging.databinding.ActivityOssNoticeBinding


class OssNoticeActivity : AppCompatActivity() {
    private val binding: ActivityOssNoticeBinding by lazy {
        ActivityOssNoticeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.noticeOssBtn.setOnClickListener {
            startActivity(Intent(this, OssLicensesMenuActivity::class.java))
        }

        binding.noticeOssEtcBtn.setOnClickListener{
            startActivity(Intent(this, EtcOssActivity::class.java))
        }
    }
}