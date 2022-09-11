package com.example.jubgging

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jubgging.databinding.FragmentFindPwBinding
import com.example.jubgging.view.FindAccountActivity

class FindPwFragment : Fragment() {

    private lateinit var binding : FragmentFindPwBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFindPwBinding.inflate(inflater, container, false)
        binding.findPwFinBt.setOnClickListener {
            binding.userInputInfoCl.visibility = View.INVISIBLE
            binding.findPwFinTv.visibility = View.VISIBLE
            binding.findPwFinBt.visibility = View.INVISIBLE
            binding.findFinPwFinBt.visibility = View.VISIBLE
        }
        return binding.root
    }

}