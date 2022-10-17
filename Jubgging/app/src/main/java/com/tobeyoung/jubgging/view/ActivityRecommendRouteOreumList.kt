package com.tobeyoung.jubgging.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityRmOreumListBinding

class ActivityRecommendRouteOreumList:AppCompatActivity() {
    private lateinit var binding:ActivityRmOreumListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rm_oreum_list)

        binding.rmOreumCl.setOnClickListener {
            val intent = Intent(this, RmJejusiActivity::class.java)
            startActivity(intent)
        }
    }
}