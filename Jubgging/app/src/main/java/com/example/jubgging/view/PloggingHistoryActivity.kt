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
import com.example.jubgging.databinding.ActivityPloggingHistoryBinding
import com.example.jubgging.databinding.ActivityPloggingHistoryItemBinding
import com.example.jubgging.network.data.request.LoginRequest
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.PloggingResponse
import com.example.jubgging.viewmodel.CleanhouseViewModel
import com.example.jubgging.viewmodel.SignUpViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoryAdapter(private val datas : MutableLiveData<BaseResponse<List<PloggingResponse>>>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){
    private var datas_list : MutableLiveData<BaseResponse<List<PloggingResponse>>> = MutableLiveData<BaseResponse<List<PloggingResponse>>>()

    init{
        datas_list = datas
    }

    interface OnItemClickListener{
        fun onItemClick(v: View, data: PloggingResponse, pos: Int)
    }
    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_plogging_history_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas_list.value!!.data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas_list.value!!.data[position])
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val historyData : TextView = itemView.findViewById(R.id.history_date_tv)
        private val historyTime : TextView = itemView.findViewById(R.id.history_time_tv)
        private val historyDistance : TextView = itemView.findViewById(R.id.history_distance_tv)
        private val historyPace : TextView = itemView.findViewById(R.id.history_pace_tv)

        fun bind(item : PloggingResponse){
            val temp : String = item.date
            var data_str = LocalDateTime.parse(temp)
            val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
            val formatted = data_str.format(formatter)

            historyData.text = formatted
            historyTime.text = item.activityTime
            historyDistance.text = item.distance.toString()
            historyPace.text = "00`00"

            val pos = adapterPosition
            if(pos!=RecyclerView.NO_POSITION){
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, item, pos)
                }
            }
        }
    }
}

class PloggingHistoryActivity : AppCompatActivity() {

    private val viewModel : CleanhouseViewModel by viewModels()
    private lateinit var binding : ActivityPloggingHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPloggingHistoryBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imsiBt.setOnClickListener {
            viewModel.plogging_res(::showToast)
        }



        viewModel.PloggingData.observe(this, Observer {
            var adapter : HistoryAdapter = HistoryAdapter(viewModel.PloggingData)
            binding.ploggingHistotyRv.adapter = adapter

            adapter.setOnItemClickListener(object:HistoryAdapter.OnItemClickListener{
                override fun onItemClick(
                    v: View,
                    data: PloggingResponse,
                    pos: Int
                ) {
                    val intent = Intent(this@PloggingHistoryActivity, PloggingDetailActivity::class.java)
                    intent.putExtra("date",data.date)
                    intent.putExtra("activityTime",data.activityTime)
                    intent.putExtra("distance",data.distance.toString())
                    intent.putExtra("recordId", data.recordId.toString())
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            })
        })

    }


    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

