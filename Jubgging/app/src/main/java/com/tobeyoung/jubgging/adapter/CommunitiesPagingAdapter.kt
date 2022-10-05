package com.tobeyoung.jubgging.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.to_be_young_jubgging.R
import com.to_be_young_jubgging.databinding.ItemCommunityGroupListBinding
import com.tobeyoung.jubgging.model.CommunityGroup
import com.tobeyoung.jubgging.view.CommunityDetailActivity
import java.text.SimpleDateFormat
import java.util.*

class CommunitiesPagingAdapter :
    PagingDataAdapter<CommunityGroup, CommunitiesPagingAdapter.CommunitiesViewHolder>(
        CommunitiesDiffCallback()) {

    override fun onBindViewHolder(holder: CommunitiesViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.itemGroupJoinBtn.setOnClickListener { v ->
            val intent = Intent(v.context,CommunityDetailActivity::class.java)
            intent.putExtra("postId",data!!.postId)
            v.context?.startActivity(intent)
        }

        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunitiesViewHolder {
        return CommunitiesViewHolder(ItemCommunityGroupListBinding.inflate(LayoutInflater.from(
            parent.context), parent, false))
    }

    inner class CommunitiesViewHolder(
        val binding: ItemCommunityGroupListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommunityGroup?) {
            binding.itemGroupDateTv.text = setDateText(data?.gatheringTime.toString())
            binding.itemGroupPlaceTv.text = data?.gatheringPlace.toString()
            binding.itemGroupNameTv.text = data?.title.toString()
            binding.itemGroupOwnerNameTv.text = data?.nickname.toString()
            binding.itemGroupPeopleTv.text = data?.participant.toString()
            binding.itemGroupPeopleTtTv.text = data?.capacity.toString()
            //날짜 계산  -> 모집 여부 enable 처리
            if(compareDate(data?.gatheringTime.toString())){
                //모집 X
                binding.itemGroupJoinBtn.isEnabled = false
                binding.itemGroupJoinBtn.setTextColor(binding.itemGroupJoinBtn.context.getColor(R.color.light_grey))
                binding.itemGroupJoinBtn.text = "모집 완료"
            }else{
                binding.itemGroupJoinBtn.isEnabled = true
                binding.itemGroupJoinBtn.setTextColor(binding.itemGroupJoinBtn.context.getColor(R.color.white))
                binding.itemGroupJoinBtn.text = "모집 중"
            }
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

    fun compareDate(gatheringDate:String):Boolean{
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)
        val gatheringD:Date = simpleDateFormat.parse(gatheringDate) as Date
        val now = System.currentTimeMillis()
        val date = Date(now)

        return gatheringD.before(date)
    }
    fun setDateText(gatheringDate: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val date = simpleDateFormat.parse(gatheringDate) as Date
        return simpleDateFormat.format(date)
    }
}