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

        val activity : FindAccountActivity = getActivity() as FindAccountActivity

        binding.findIdFinBt.setOnClickListener {
            activity.replaceView(FindIdFinFragment())
        }

        return binding.root
    }
}