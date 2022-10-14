package com.tobeyoung.jubgging.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tobeyoung.jubgging.view.MyCommunityCreatedFragment
import com.tobeyoung.jubgging.view.MyCommunityJoinedFragment

class MyCommunityViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
        private val pageNum = 2
        override fun getItemCount(): Int = pageNum
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> MyCommunityJoinedFragment()
                1 -> MyCommunityCreatedFragment()
                else -> MyCommunityJoinedFragment()
            }
        }
    }
