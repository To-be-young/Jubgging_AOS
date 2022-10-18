package com.tobeyoung.jubgging.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityRmAttractionListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmJejusiListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmJejusiWesternListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmOceanListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmOlleListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmOreumListBinding

class ActivityRecommendRouteJejusiWesternList:AppCompatActivity() {
    private lateinit var binding:ActivityRmJejusiWesternListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rm_jejusi_western_list)

        binding.rmJejusiWesternCl.setOnClickListener {
            val intent = Intent(this, RmJejusiWesternActivity::class.java)
            startActivity(intent)
        }
    }
}