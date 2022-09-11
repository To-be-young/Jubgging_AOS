package com.example.jubgging

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jubgging.databinding.FragmentFindIdBinding
import com.example.jubgging.view.FindAccountActivity

class FindIdFragment : Fragment() {

    private lateinit var binding : FragmentFindIdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindIdBinding.inflate(inflater, container, false)

        binding.findIdFinBt.setOnClickListener {
            binding.userInputInfoCl.visibility = View.INVISIBLE
            binding.findIdUserEmailInfoCl.visibility = View.VISIBLE
            binding.findFinIdFinBt.visibility = View.VISIBLE
            binding.findIdFinBt.visibility = View.INVISIBLE
        }
        return binding.root
    }
}