package com.tobeyoung.jubgging.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityRmAttractionListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmJejusiEasternListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmJejusiListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmJejusiWesternListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmOceanListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmOlleListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmOreumListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmSeogwipoEasternListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmSeogwipoListBinding
import com.tobeyoung.jubgging.databinding.ActivityRmSeogwipoWesternListBinding

class ActivityRecommendRouteSeogwipoList:AppCompatActivity() {
    private lateinit var binding:ActivityRmSeogwipoListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rm_seogwipo_list)

    }
}