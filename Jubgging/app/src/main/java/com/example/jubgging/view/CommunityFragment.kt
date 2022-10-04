package com.example.jubgging.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jubgging.R
import com.example.jubgging.adapter.CommunityEventRecyclerViewAdapter
import com.example.jubgging.adapter.CommunityGroupPagingAdapter
import com.example.jubgging.databinding.FragmentCommunityBinding
import com.example.jubgging.model.CommunityEvent
import com.example.jubgging.viewmodel.CommunityViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CommunityFragment : Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    private val viewModel:CommunityViewModel by viewModels()
    private val adapter = CommunityGroupPagingAdapter()
    private var searchJob: Job? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_community, container, false)
        binding.lifecycleOwner = this
        binding.communityVm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.communityGroupRv.adapter = adapter
        binding.communityGroupRv.layoutManager = LinearLayoutManager(requireContext())

        startGetlist()

        binding.communityGroupMoreCl.setOnClickListener {
            val intent = Intent(requireContext(),CommunityListActivity::class.java)
            startActivity(intent)
        }


        val communityEventRecyclerViewAdapter = CommunityEventRecyclerViewAdapter()
        binding.communityEventRv.adapter = communityEventRecyclerViewAdapter

        var list : MutableList<CommunityEvent> = mutableListOf<CommunityEvent>()
        list.add(0, CommunityEvent("걸으멍 도르멍 주시멍", context?.let { ContextCompat.getDrawable(it, R.drawable.plogging_poster1) }!!))
        list.add(0, CommunityEvent("함께할래? 제주랑 플로깅 V-log", context?.let { ContextCompat.getDrawable(it, R.drawable.plogging_poster2) }!!))
        list.add(0, CommunityEvent("강릉 해변 플로깅 캠페인 쓰담해", context?.let { ContextCompat.getDrawable(it, R.drawable.plogging_poster3) }!!))
        list.add(0, CommunityEvent("아트 플로깅 '기억을 줍다'", context?.let { ContextCompat.getDrawable(it, R.drawable.plogging_poster4) }!!))
        list.add(0, CommunityEvent("플로깅 챌린지 모집", context?.let { ContextCompat.getDrawable(it, R.drawable.plogging_poster5) }!!))

        communityEventRecyclerViewAdapter.submitCommunityEventList(list)
    }
    private fun startGetlist() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getList().observe(requireActivity()) {
                adapter.submitData(requireActivity().lifecycle, it)
            }
        }
    }
}