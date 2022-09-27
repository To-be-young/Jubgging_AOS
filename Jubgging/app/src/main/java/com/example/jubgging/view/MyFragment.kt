package com.example.jubgging.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.jubgging.R
import com.example.jubgging.databinding.FragmentMyBinding

class MyFragment: Fragment() {
    private lateinit var binding: FragmentMyBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myInfoBtn.setOnClickListener {
            val intent = Intent(requireContext(),UserProfileActivity::class.java)
            startActivity(intent)
        }
    }
}