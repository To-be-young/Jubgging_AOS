package com.tobeyoung.jubgging.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityRmOceanListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmOreumListBinding

class ActivityRecommendRouteOceanList:AppCompatActivity() {
    private lateinit var binding:ActivityRmOceanListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rm_ocean_list)

        binding.rmOceanCl.setOnClickListener {
            val intent = Intent(this, RmJejusiWesternActivity::class.java)
            startActivity(intent)
        }
    }
}