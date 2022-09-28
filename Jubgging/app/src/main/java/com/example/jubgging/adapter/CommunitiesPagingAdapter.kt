package com.example.jubgging.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jubgging.databinding.ItemCommunityGroupListBinding
import com.example.jubgging.model.CommunityGroup

class CommunitiesPagingAdapter :
    PagingDataAdapter<CommunityGroup, CommunitiesPagingAdapter.CommunitiesViewHolder>(
        CommunitiesDiffCallback()) {
    override fun onBindViewHolder(holder: CommunitiesViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunitiesViewHolder {
        return CommunitiesViewHolder(ItemCommunityGroupListBinding.inflate(LayoutInflater.from(
            parent.context), parent, false))
    }

    inner class CommunitiesViewHolder(
        private val binding: ItemCommunityGroupListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommunityGroup?) {
            binding.itemGroupNameTv.text = data?.title.toString()
            Log.d("TAG", "bind: ${data?.title.toString()}")
        }

    }

    private class CommunitiesDiffCallback : DiffUtil.ItemCallback<CommunityGroup>() {
        override fun areItemsTheSame(oldItem: CommunityGroup, newItem: CommunityGroup): Boolean {
            return oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(oldItem: CommunityGroup, newItem: CommunityGroup): Boolean {
            return oldItem == newItem
        }

    }
}