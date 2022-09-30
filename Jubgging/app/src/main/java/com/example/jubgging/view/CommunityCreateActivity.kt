package com.example.jubgging.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityCommunityGroupCreateBinding
import com.example.jubgging.network.data.request.PostCommunityRequest
import com.example.jubgging.viewmodel.CommunityViewModel
import java.util.*

class CommunityCreateActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCommunityGroupCreateBinding
    private val viewModel: CommunityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_group_create)
        binding.lifecycleOwner = this
        binding.communityVm = viewModel

        val title = binding.cgcNameTv.text.toString()
        val userId = "asd"
        val content = binding.cgcDescEt.toString()
        val qualification = binding.cgcNoticeFirstEt.text.toString()
        val gatheringTime :String = "${viewModel.date.value.toString()}+${viewModel}"
        val endingTime:String
        val gatheringPlace = binding.cgcPlaceEt.text.toString()
        val capacity = binding.cgcPeopleEt.toString()
        val etc  = binding.cgcEtcEt.text.toString()
        val postImage = null

        binding.cgcDateTv.setOnClickListener (this)
        binding.cgcStartTimeTv.setOnClickListener (this)
        binding.cgcEndTimeTv.setOnClickListener (this)


        binding.cgcCreateBtn.setOnClickListener {
//            viewModel.postingCommunity(postCommunityRequest = PostCommunityRequest())
        }

    }

    private fun showCalendar(context:Context){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        var selectedDate:String =""
        val listener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                if(month>10 && dayOfMonth>10){
                    selectedDate = "$year-$month-$dayOfMonth"
                }else if(month<10 && dayOfMonth >10){
                    selectedDate =  "$year-0$month-$dayOfMonth"
                }else if(month>10 && dayOfMonth <10){
                    selectedDate =  "$year-$month-0$dayOfMonth"
                }else{
                    selectedDate =  "$year-0$month-0$dayOfMonth"
                }

                viewModel.updateDate(selectedDate)
            }
        val picker = DatePickerDialog(context, listener, year, month, day)
        picker.datePicker.minDate = System.currentTimeMillis()
        picker.show()

    }

    private fun showStartTimePicker(context: Context){
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        var selectedTime:String = ""
        val listener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            if(hour>=10 && minute>=10){
                selectedTime = "$hour:$minute"
            }else if(hour<10 && minute >=10){
                selectedTime =  "0$hour:$minute"
            }else if(hour>=10 && minute <10){
                selectedTime =  "$hour:0$minute"
            }else{
                selectedTime =  "0$hour:0$minute"
            }

            viewModel.updateSTime(selectedTime)
        }
        val picker = TimePickerDialog(context,listener,hour,minute,true)
        picker.setTitle("시작 시간")
        picker.show()
    }
    private fun showEndTimePicker(context: Context){
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        var selectedTime:String = ""
        val listener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            if(hour>=10 && minute>=10){
                selectedTime = "$hour:$minute"
            }else if(hour<10 && minute >=10){
                selectedTime =  "0$hour:$minute"
            }else if(hour>=10 && minute <10){
                selectedTime =  "$hour:0$minute"
            }else{
                selectedTime =  "0$hour:0$minute"
            }
            viewModel.updateETime(selectedTime)
        }
        val picker = TimePickerDialog(context,listener,hour,minute,true)
        picker.setTitle("종료 시간")
        picker.show()
    }

    override fun onClick(p0: View?) {
       when(p0){
           binding.cgcDateTv ->{
               showCalendar(this)
           }
           binding.cgcStartTimeTv ->{
                showStartTimePicker(this)
           }
           binding.cgcEndTimeTv->{
               showEndTimePicker(this)
           }
       }
    }
}