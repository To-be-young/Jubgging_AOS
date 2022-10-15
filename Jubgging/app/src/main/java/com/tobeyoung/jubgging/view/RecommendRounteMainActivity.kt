package com.tobeyoung.jubgging.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.adapter.PloggingGuideRecyclerViewAdapter
import com.tobeyoung.jubgging.databinding.ActivityRecommendRounteMainBinding
import com.tobeyoung.jubgging.model.PloggingGuide

class RecommendRounteMainActivity : AppCompatActivity() {
    private val adapter = PloggingGuideRecyclerViewAdapter()
    private lateinit var binding : ActivityRecommendRounteMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRecommendRounteMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recommendPloggingGuideRv.adapter = adapter
        binding.recommendPloggingGuideRv.layoutManager = LinearLayoutManager(this)


        val ploggingGuideRecyclerViewAdapter = PloggingGuideRecyclerViewAdapter()
        binding.recommendPloggingGuideRv.adapter = ploggingGuideRecyclerViewAdapter

        var list : MutableList<PloggingGuide> = mutableListOf<PloggingGuide>()
        list.add(0, PloggingGuide("걸으멍 도르멍 주시멍", resources.getDrawable(R.drawable.plogging_poster1)))


        ploggingGuideRecyclerViewAdapter.submitploggingGuideList(list)

        binding.recommendOlleroadCl.setOnClickListener{
            val intent = Intent(this,ActivityRecommendRouteOlleList::class.java)
            startActivity(intent)
        }
        binding.recommendSeaCl.setOnClickListener {
            val intent = Intent(this,ActivityRecommendRouteOceanList::class.java)
            startActivity(intent)
        }
        binding.recommendTourCl.setOnClickListener {
            val intent = Intent(this,ActivityRecommendRouteAttractionList::class.java)
            startActivity(intent)
        }
        binding.recommendOreumCl.setOnClickListener {
            val intent = Intent(this,ActivityRecommendRouteOreumList::class.java)
            startActivity(intent)
        }
    }
}