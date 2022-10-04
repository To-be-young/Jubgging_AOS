package com.example.jubgging.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jubgging.databinding.ActivityPloggingHistoryItemBinding
import com.example.jubgging.model.HistoryGroup
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoryPagingAdapter :
    PagingDataAdapter<HistoryGroup, HistoryPagingAdapter.HistoriedViewHolder>(
        CommunitiesDiffCallback()) {

    //Click Listener 적용
    interface OnItemClickListener{
        fun onItemClick(v: View, data: HistoryGroup, pos: Int)
    }

    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener){
        this.listener = listener
    }


    override fun onBindViewHolder(holder: HistoriedViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriedViewHolder {
        return HistoriedViewHolder(ActivityPloggingHistoryItemBinding.inflate(
            LayoutInflater.from(
            parent.context), parent, false))
    }

    inner class HistoriedViewHolder(
        private val binding: ActivityPloggingHistoryItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        //ItemView가져오기
        private val historyData : TextView = binding.historyDateTv
        private val historyTime : TextView = binding.historyTimeTv
        private val historyDistance : TextView = binding.historyDistanceTv
        private val historyPace : TextView = binding.historyPaceTv

        fun bind(data: HistoryGroup?) {
//            binding.itemGroupNameTv.text = data?.title.toString()
//            Log.d("TAG", "bind: ${data?.title.toString()}")

            val temp : String = data!!.date
            var data_str = LocalDateTime.parse(temp)
            val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
            val formatted = data_str.format(formatter)

            historyData.text = formatted
            historyTime.text = data.activityTime
            historyDistance.text = data.distance.toString()
            historyPace.text = "00`00"


            val pos = adapterPosition

            if(pos!=RecyclerView.NO_POSITION){
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, data, pos)
                }
            }


        }

    }

    private class CommunitiesDiffCallback : DiffUtil.ItemCallback<HistoryGroup>() {
        override fun areItemsTheSame(oldItem: HistoryGroup, newItem: HistoryGroup): Boolean {
            return oldItem.recordId == newItem.recordId
        }

        override fun areContentsTheSame(oldItem: HistoryGroup, newItem: HistoryGroup): Boolean {
            return oldItem == newItem
        }

    }
}