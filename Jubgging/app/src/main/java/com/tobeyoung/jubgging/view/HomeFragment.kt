package com.tobeyoung.jubgging.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.FragmentHomeBinding
import com.tobeyoung.jubgging.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private  val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.homeVm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeChMapCl.setOnClickListener {
            val intent = Intent(requireContext(), CleanHouseMapActivity::class.java)
            startActivity(intent)
        }
        binding.homePloggingCl.setOnClickListener {
            val intent = Intent(requireContext(), PloggingActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        viewModel.getUserNickname()

    }
}