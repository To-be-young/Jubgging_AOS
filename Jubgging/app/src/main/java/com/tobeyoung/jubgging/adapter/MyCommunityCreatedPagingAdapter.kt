package com.tobeyoung.jubgging.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tobeyoung.jubgging.databinding.ItemMyCommunityCreatedBinding
import com.tobeyoung.jubgging.model.CommunityGroup
import com.tobeyoung.jubgging.view.CommunityDetailActivity
import java.text.SimpleDateFormat
import java.util.*

class MyCommunityCreatedPagingAdapter :
    PagingDataAdapter<CommunityGroup, MyCommunityCreatedPagingAdapter.CommunityGroupViewHolder>(
MyCommunitiesCreatedDiffCallback()) {
    override fun onBindViewHolder(holder: CommunityGroupViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.itemMccSettingBtn.setOnClickListener { v ->
            val intent = Intent(v.context, CommunityDetailActivity::class.java)
            intent.putExtra("postId",data!!.postId)
            v.context?.startActivity(intent)
        }

        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityGroupViewHolder {
        return CommunityGroupViewHolder(ItemMyCommunityCreatedBinding.inflate(LayoutInflater.from(
            parent.context), parent, false))
    }

    inner class CommunityGroupViewHolder(
        val binding: ItemMyCommunityCreatedBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommunityGroup?) {
            binding.itemMccDateTv.text = setDateText(data?.gatheringTime.toString())
            binding.itemMccPlaceTv.text = data?.gatheringPlace.toString()
            binding.itemMccNameTv.text = data?.title.toString()
            binding.itemMccOwnerNameTv.text = data?.nickname.toString()
            binding.itemMccPeopleTv.text = data?.participant.toString()
            binding.itemMccPeopleTtTv.text = data?.capacity.toString()
        }

    }

    private class MyCommunitiesCreatedDiffCallback : DiffUtil.ItemCallback<CommunityGroup>() {
        override fun areItemsTheSame(oldItem: CommunityGroup, newItem: CommunityGroup): Boolean {
            return oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(oldItem: CommunityGroup, newItem: CommunityGroup): Boolean {
            return oldItem == newItem
        }

    }

    fun setDateText(gatheringDate: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val date = simpleDateFormat.parse(gatheringDate) as Date
        return simpleDateFormat.format(date)
    }
}