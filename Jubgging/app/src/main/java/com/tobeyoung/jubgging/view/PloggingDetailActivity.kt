package com.tobeyoung.jubgging.view

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.tobeyoung.jubgging.databinding.ActivityPloggingDetailBinding
import com.tobeyoung.jubgging.viewmodel.PathwayViewModel
import net.daum.mf.map.api.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PloggingDetailActivity : AppCompatActivity(), MapView.CurrentLocationEventListener,
    MapView.MapViewEventListener,
    MapView.POIItemEventListener {

    //viewBinding
    private lateinit var binding: ActivityPloggingDetailBinding

    private lateinit var mapView: MapView //카카오맵뷰
    private lateinit var mapViewContainer: ViewGroup

    //ViewModel
    private val pathwayViewMoodel: PathwayViewModel by viewModels()

    //폴리라인 관련 변수
    private lateinit var polyline: MapPolyline

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPloggingDetailBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val IntentDate = intent.getStringExtra("date")
        val IntenTime = intent.getStringExtra("activityTime")
        val IntentDistance = intent.getStringExtra("distance")
        val IntentRecordId = intent.getStringExtra("recordId")
        val IntentPace = intent.getStringExtra("pace")

        var data_str = LocalDateTime.parse(IntentDate)
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
        val formatted = data_str.format(formatter)

        binding.historyDateTv.text = formatted
        binding.historyTimeTv.text = IntenTime
        binding.historyDistanceTv.text = IntentDistance
        binding.historyPaceTv.text = IntentPace

        pathwayViewMoodel.pathway(IntentRecordId!!.toInt(), ::showToast)
        Log.d("success_pathway", "${IntentRecordId.toInt()}")
    }

    override fun onResume() {
        super.onResume()

        //맵뷰 등록
        mapView = MapView(this)
        mapViewContainer = binding.ploggingKakaoMapView
        mapViewContainer.addView(mapView)


        //mapView에 이벤트 등록
        mapView.setMapViewEventListener(this)
        mapView.setPOIItemEventListener(this)
        mapView.setCurrentLocationEventListener(this)

        //polyline 등록
        polyline = MapPolyline()
        polyline.tag = 1000
        polyline.lineColor = Color.argb(255, 0, 183, 134)

        pathwayViewMoodel.PathwayData.observe(this, Observer {

            Log.d("data_size", "${pathwayViewMoodel.PathwayData.value!!.data.size}")
            //받아온 liveData를 Polyline객체에 넣어서 선 그려주기
            for(i in 0 until pathwayViewMoodel.PathwayData.value!!.data.size){
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(pathwayViewMoodel.PathwayData.value!!.data[i].latitude, pathwayViewMoodel.PathwayData.value!!.data[i].longitude))
            }

            mapView.addPolyline(polyline)

            var mapPointBounds: MapPointBounds = MapPointBounds(polyline.mapPoints)
            val padding: Int = 100

            mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding))
        })

    }


    override fun onPause() {
        super.onPause()
        pathwayViewMoodel.PathwayData.removeObservers(this)
        mapViewContainer.removeAllViews()
    }


    override fun onCurrentLocationUpdate(p0: MapView?, p1: MapPoint?, p2: Float) {

    }

    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {

    }

    override fun onCurrentLocationUpdateFailed(p0: MapView?) {

    }

    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {

    }

    override fun onMapViewInitialized(p0: MapView?) {

    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {

    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {

    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {

    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {

    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {

    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {

    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, PloggingHistoryActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}