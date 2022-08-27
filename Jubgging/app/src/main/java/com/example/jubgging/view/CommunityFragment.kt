package com.example.jubgging.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.jubgging.R
import com.example.jubgging.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_community, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }
}