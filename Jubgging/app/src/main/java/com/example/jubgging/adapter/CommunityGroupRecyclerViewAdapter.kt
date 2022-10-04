package com.example.jubgging.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jubgging.R
import com.example.jubgging.model.CommunityGroup

class CommunityGroupRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val ITEM_COUNT = 6
    }

    private var communityGroupList: List<CommunityGroup>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommunityGroupViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_community_group, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        communityGroupList?.let { communityGroup ->
            (holder as CommunityGroupViewHolder).bind(communityGroup[position])
        }
    }

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    fun submitCommunityGroupList(list: List<CommunityGroup>) {
        communityGroupList = list
        notifyDataSetChanged()
    }

    class CommunityGroupViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(communityGroup: CommunityGroup ) {
            //communityGroup. = "제주대학교"
        }
    }
}