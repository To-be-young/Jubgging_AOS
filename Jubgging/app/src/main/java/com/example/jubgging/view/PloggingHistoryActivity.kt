package com.example.jubgging.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jubgging.R
import com.example.jubgging.adapter.HistoryAdapter
import com.example.jubgging.databinding.ActivityPloggingHistoryBinding
import com.example.jubgging.databinding.ActivityPloggingHistoryItemBinding
import com.example.jubgging.network.PloggingReceive
import com.example.jubgging.network.data.request.LoginRequest
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.PloggingResponse
import com.example.jubgging.viewmodel.CleanhouseViewModel
import com.example.jubgging.viewmodel.SignUpViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PloggingHistoryActivity : AppCompatActivity() {

    private val viewModel : CleanhouseViewModel by viewModels()
    private lateinit var binding : ActivityPloggingHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPloggingHistoryBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imsiBt.setOnClickListener {
            viewModel.plogging_res(0, ::showToast)
        }



        viewModel.PloggingData.observe(this, Observer {
            var adapter : HistoryAdapter = HistoryAdapter(viewModel.PloggingData)
            binding.ploggingHistotyRv.adapter = adapter

//            adapter.setOnItemClickListener(object:HistoryAdapter.OnItemClickListener{
//                override fun onItemClick(
//                    v: View,
//                    data: PloggingResponse,
//                    pos: Int
//                ) {
//                    val intent = Intent(this@PloggingHistoryActivity, PloggingDetailActivity::class.java)
//                    intent.putExtra("date",data.date)
//                    intent.putExtra("activityTime",data.activityTime)
//                    intent.putExtra("distance",data.distance.toString())
//                    intent.putExtra("recordId", data.recordId.toString())
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    startActivity(intent)
//                }
//            })
        })

    }


    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

