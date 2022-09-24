package com.example.jubgging.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.PloggingResponse
import com.example.jubgging.viewmodel.CleanhouseViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//class MyAdapter(private val datas : MutableLiveData<BaseResponse<List<PloggingResponse>>>) : RecyclerView.Adapter<MyAdapter.CustomViewHolder>(){
//
//    private var datas_list : MutableLiveData<BaseResponse<List<PloggingResponse>>> = MutableLiveData<BaseResponse<List<PloggingResponse>>>()
//
//    init{
//        datas_list = datas
//    }
//    override fun getItemCount(): Int {
//        return datas.value!!.data.size
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.CustomViewHolder{
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_plogging_history_item, parent, false)
////        return CustomViewHolder(view).apply{
////            val curPos : Int = adapterPosition
////            val record : PloggingResponse = datas_list.value!!.data[curPos]
////        }
//
//        return CustomViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: MyAdapter.CustomViewHolder, position: Int) {
//        val memo = datas_list.value!!.data[position]
//        h
//    }
//
//    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
//
//    }
//}

class HistoryAdapter(private val datas : MutableLiveData<BaseResponse<List<PloggingResponse>>>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){
    private var datas_list : MutableLiveData<BaseResponse<List<PloggingResponse>>> = MutableLiveData<BaseResponse<List<PloggingResponse>>>()

    init{
        datas_list = datas
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
        }
    }
}

class PloggingHistoryActivity : AppCompatActivity() {

    private val viewModel : CleanhouseViewModel by viewModels()
    private lateinit var binding : ActivityPloggingHistoryBinding
    private val userId : String = "grand2181@gmail.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPloggingHistoryBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imsiBt.setOnClickListener {
            viewModel.plogging_res(userId, ::showToast)
        }


        viewModel.PloggingData.observe(this, Observer {
            binding.ploggingHistotyRv.adapter = HistoryAdapter(viewModel.PloggingData)
        })
    }


    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}

