package com.tobeyoung.jubgging.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.adapter.PloggingGuideRecyclerViewAdapter
import com.tobeyoung.jubgging.databinding.ActivityRecommendRounteMainBinding
import com.tobeyoung.jubgging.model.PloggingGuide

class RecommendRouteMainActivity : AppCompatActivity() {
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
        binding.recommendJejuCl.setOnClickListener {
            val intent = Intent(this,ActivityRecommendRouteJejusiList::class.java)
            startActivity(intent)
        }
        binding.recommendJejuEastCl.setOnClickListener {
            val intent = Intent(this,ActivityRecommendRouteJejusiEasternList::class.java)
            startActivity(intent)
        }
        binding.recommendJejuWestCl.setOnClickListener {
            val intent = Intent(this,ActivityRecommendRouteJejusiWesternList::class.java)
            startActivity(intent)
        }
        binding.recommendSeogwipoCl.setOnClickListener {
            val intent = Intent(this,ActivityRecommendRouteSeogwipoList::class.java)
            startActivity(intent)
        }
        binding.recommendSeogwipoEastCl.setOnClickListener {
            val intent = Intent(this,ActivityRecommendRouteSeogwipoEasternList::class.java)
            startActivity(intent)
        }
        binding.recommendSeogwipoWestCl.setOnClickListener {
            val intent = Intent(this,ActivityRecommendRouteSeogwipoWesternList::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}