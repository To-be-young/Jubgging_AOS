package com.example.jubgging.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jubgging.R
import com.example.jubgging.model.CommunityEvent

class CommunityEventRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var communityEventList: List<CommunityEvent>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommunityEventViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_community_event, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        communityEventList?.let { communityEvent ->
            (holder as CommunityEventViewHolder).bind(communityEvent[position])
        }
    }

    override fun getItemCount(): Int {
        return communityEventList!!.size
  }
    fun submitCommunityEventList(list: List<CommunityEvent>) {
        communityEventList = list
        notifyDataSetChanged()
    }
    class CommunityEventViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val item : TextView = itemView.findViewById(R.id.item_event_tv)
        val img : ImageView = itemView.findViewById(R.id.item_event_iv)
        fun bind(communityEvent: CommunityEvent) {
            item.text = communityEvent.eventName
            img.setImageDrawable(communityEvent.img)
        }
    }
}