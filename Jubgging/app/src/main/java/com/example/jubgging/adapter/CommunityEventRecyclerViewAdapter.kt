package com.example.jubgging.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jubgging.R
import com.example.jubgging.model.CommunityEvent

class CommunityEventRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val ITEM_COUNT = 6
    }

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
        return ITEM_COUNT
    }
    fun submitCommunityEventList(list: List<CommunityEvent>) {
        communityEventList = list
        notifyDataSetChanged()
    }
    class CommunityEventViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(communityEvent: CommunityEvent) {
            communityEvent.eventName = "제주대학교"
        }
    }
}