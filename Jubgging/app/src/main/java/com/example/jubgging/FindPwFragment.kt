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

        val activity : FindAccountActivity = getActivity() as FindAccountActivity

        binding.findPwFinBt.setOnClickListener {
            activity.replaceView(FindPwFinFragment())
        }
        return binding.root
    }

}