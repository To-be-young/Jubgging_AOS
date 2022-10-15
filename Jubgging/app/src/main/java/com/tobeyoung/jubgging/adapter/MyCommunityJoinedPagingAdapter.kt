package com.tobeyoung.jubgging.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ItemCommunityGroupListBinding
import com.tobeyoung.jubgging.model.CommunityGroup
import com.tobeyoung.jubgging.network.data.response.CommunityJoinResponse
import com.tobeyoung.jubgging.view.CommunityDetailActivity
import java.text.SimpleDateFormat
import java.util.*

class MyCommunityJoinedPagingAdapter :
    PagingDataAdapter<CommunityJoinResponse<CommunityGroup>, MyCommunityJoinedPagingAdapter.CommunityGroupViewHolder>(
        MyCommunitiesJoinedDiffCallback()) {
    override fun onBindViewHolder(holder: CommunityGroupViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.itemGroupJoinBtn.setOnClickListener { v ->
            val intent = Intent(v.context, CommunityDetailActivity::class.java)
            intent.putExtra("postId",data!!.postId.postId)
            v.context?.startActivity(intent)
        }

        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityGroupViewHolder {
        return CommunityGroupViewHolder(ItemCommunityGroupListBinding.inflate(LayoutInflater.from(
            parent.context), parent, false))
    }

    inner class CommunityGroupViewHolder(
        val binding: ItemCommunityGroupListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommunityJoinResponse<CommunityGroup>?) {

            binding.itemGroupDateTv.text = setDateText(data?.postId?.gatheringTime.toString())
            binding.itemGroupPlaceTv.text = data?.postId?.gatheringPlace.toString()
            binding.itemGroupNameTv.text = data?.postId?.title.toString()
            binding.itemGroupOwnerNameTv.text = data?.postId?.nickname.toString()
            binding.itemGroupPeopleTv.text = data?.postId?.participant.toString()
            binding.itemGroupPeopleTtTv.text = data?.postId?.capacity.toString()
            //날짜 계산  -> 모집 여부 enable 처리
            if(compareDate(data?.postId?.gatheringTime.toString())){
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

    private class MyCommunitiesJoinedDiffCallback : DiffUtil.ItemCallback<CommunityJoinResponse<CommunityGroup>>() {
        override fun areItemsTheSame(oldItem: CommunityJoinResponse<CommunityGroup>, newItem: CommunityJoinResponse<CommunityGroup>): Boolean {
            return oldItem.postId.postId == newItem.postId.postId
        }

        override fun areContentsTheSame(oldItem: CommunityJoinResponse<CommunityGroup>, newItem: CommunityJoinResponse<CommunityGroup>): Boolean {
            return oldItem.postId == newItem.postId
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