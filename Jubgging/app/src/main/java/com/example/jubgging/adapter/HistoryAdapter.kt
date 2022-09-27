package com.example.jubgging.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.jubgging.R
import com.example.jubgging.network.PloggingReceive
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class HistoryAdapter(private val datas: MutableLiveData<List<PloggingReceive>>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){
    private var datas_list : MutableLiveData<List<PloggingReceive>> = MutableLiveData<List<PloggingReceive>>()

    init{
        datas_list = datas
    }

    //Click Listener 적용
    interface OnItemClickListener{
        fun onItemClick(v: View, data: PloggingReceive, pos: Int)
    }

    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_plogging_history_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas_list.value!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas_list.value!![position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        //ItemView가져오기
        private val historyData : TextView = itemView.findViewById(R.id.history_date_tv)
        private val historyTime : TextView = itemView.findViewById(R.id.history_time_tv)
        private val historyDistance : TextView = itemView.findViewById(R.id.history_distance_tv)
        private val historyPace : TextView = itemView.findViewById(R.id.history_pace_tv)

        fun bind(item : PloggingReceive){
            //가져온 ItemView의 값들을 textView에 뿌려줌

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