package com.tobeyoung.jubgging.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityRmAttractionListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmOceanListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmOlleListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmOreumListBinding

class ActivityRecommendRouteAttractionList:AppCompatActivity() {
    private lateinit var binding:ActivityRmAttractionListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rm_attraction_list)

    }
}