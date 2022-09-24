package com.example.jubgging.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityPloggingHistoryBinding
import com.example.jubgging.databinding.ActivityPloggingHistoryItemBinding

class ViewHolder(val binding : ActivityPloggingHistoryItemBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(val datas:MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(ActivityPloggingHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }
}
class PloggingHistoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPloggingHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPloggingHistoryBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val datas = mutableListOf<String>()
        for(i in 1..9){
            datas.add("Item $i")
        }

        binding.ploggingHistotyRv.adapter = MyAdapter(datas)


    }
}

