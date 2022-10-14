package com.tobeyoung.jubgging.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.FragmentMyCommunityCreatedBinding
import com.tobeyoung.jubgging.viewmodel.CommunityViewModel

class MyCommunityCreatedFragment : Fragment() {
    private lateinit var binding: FragmentMyCommunityCreatedBinding
    private val viewModel: CommunityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_community_created,container,false)
        binding.communityVm = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}