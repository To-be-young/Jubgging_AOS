package com.tobeyoung.jubgging.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.adapter.MyCommunityCreatedPagingAdapter
import com.tobeyoung.jubgging.databinding.FragmentMyCommunityCreatedBinding
import com.tobeyoung.jubgging.viewmodel.CommunityViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyCommunityCreatedFragment : Fragment() {
    private lateinit var binding: FragmentMyCommunityCreatedBinding
    private val viewModel: CommunityViewModel by viewModels()
    private val adapter = MyCommunityCreatedPagingAdapter()
    private var searchJob: Job? = null
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mccRv.adapter = adapter
        binding.mccRv.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun onResume() {
        super.onResume()
        startGetlist()
    }
    private fun startGetlist() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getMyCommunityList().observe(requireActivity()) {
                adapter.submitData(requireActivity().lifecycle, it)
            }
        }
    }
}