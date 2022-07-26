package com.tobeyoung.jubgging.view

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tobeyoung.jubgging.databinding.ActivityPloggingHistoryBinding
import com.tobeyoung.jubgging.model.HistoryGroup
import com.tobeyoung.jubgging.paging.HistoryPagingAdapter
import com.tobeyoung.jubgging.viewmodel.CleanhouseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PloggingHistoryActivity : AppCompatActivity() {

    private val viewModel : CleanhouseViewModel by viewModels()
    private lateinit var binding : ActivityPloggingHistoryBinding
    private val adapter =  HistoryPagingAdapter()
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPloggingHistoryBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ploggingHistotyRv.adapter = adapter
        binding.ploggingHistotyRv.layoutManager = LinearLayoutManager(this)

        adapter.setOnItemClickListener(object: HistoryPagingAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: HistoryGroup, pos: Int) {
                val intent = Intent(this@PloggingHistoryActivity, PloggingDetailActivity::class.java)
                intent.putExtra("date",data.date)
                intent.putExtra("activityTime",data.activityTime)
                intent.putExtra("distance",data.distance.toString())
                intent.putExtra("pace", data.pace)
                intent.putExtra("recordId", data.recordId.toString())
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })

        startGetlist()

    }

    private fun startGetlist() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getList().observe(this@PloggingHistoryActivity) {
                adapter.submitData(this@PloggingHistoryActivity.lifecycle, it)
            }
        }
    }


    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

