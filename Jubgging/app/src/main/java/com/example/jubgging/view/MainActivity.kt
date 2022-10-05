package com.example.jubgging.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var backpressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

        initNavigation()

    }

    private fun initNavigation() {
        NavigationUI.setupWithNavController(binding.bottomNavigationView,
            findNavController(R.id.navi_host))
    }

    //Listener역할을 할 Interface 생성
    interface onBackPressedListener {
        fun onBackPressed()
    }

    override fun onBackPressed(){
        //아래와 같은 코드를 추가하도록 한다
        //해당 엑티비티에서 띄운 프래그먼트에서 뒤로가기를 누르게 되면 프래그먼트에서 구현한 onBackPressed 함수가 실행되게 된다.
        val fragmentList = supportFragmentManager.fragments
        for (fragment in fragmentList) {
            if (fragment is onBackPressedListener) {
                (fragment as onBackPressedListener).onBackPressed()
                return
            }
        }
        if(System.currentTimeMillis() > backpressedTime + 2000){
            backpressedTime = System.currentTimeMillis()
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show()
        }else if(System.currentTimeMillis() <= backpressedTime + 2000){
            finish()
        }
    }
}
