package com.example.jubgging.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jubgging.R
import com.example.jubgging.databinding.FragmentMyBinding
import com.example.jubgging.viewmodel.UserInfoViewModel

class MyFragment : Fragment() {
    private lateinit var binding: FragmentMyBinding
    private val viewModel: UserInfoViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false)
        binding.userVm = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myUserProfileCl.setOnClickListener {
            val intent = Intent(requireContext(), UserProfileActivity::class.java)
            startActivity(intent)
        }

        binding.myNoticeCl.setOnClickListener {
            val intent = Intent(requireContext(), OssNoticeActivity::class.java)
            startActivity(intent)
        }
        binding.myPloggingListCl.setOnClickListener {
            val intent = Intent(requireContext(),PloggingHistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserInfo()
    }

}