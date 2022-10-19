package com.tobeyoung.jubgging.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityRmAttractionListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmJejusiListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmOceanListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmOlleListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmOreumListBinding

class ActivityRecommendRouteJejusiList:AppCompatActivity() {
    private lateinit var binding:ActivityRmJejusiListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rm_jejusi_list)

        binding.rmJejusiCl.setOnClickListener {
            val intent = Intent(this, RmJejusiActivity::class.java)
            startActivity(intent)
        }
    }
}