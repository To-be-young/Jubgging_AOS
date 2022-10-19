package com.tobeyoung.jubgging.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.model.PloggingGuide

class PloggingGuideRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var ploggingGuideList: List<PloggingGuide>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PloggingGuideViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.item_community_event, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        ploggingGuideList?.let { PloggingGuide ->
            (holder as PloggingGuideViewHolder).bind(PloggingGuide[position])
        }
    }

    override fun getItemCount(): Int {
        return ploggingGuideList!!.size
    }
    fun submitploggingGuideList(list: List<PloggingGuide>) {
        ploggingGuideList = list
        notifyDataSetChanged()
    }
    class PloggingGuideViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val item : TextView = itemView.findViewById(R.id.item_event_tv)
        val img : ImageView = itemView.findViewById(R.id.item_event_iv)
        fun bind(communityEvent: PloggingGuide) {
            item.text = communityEvent.eventName
            img.setImageDrawable(communityEvent.img)
        }
    }
}