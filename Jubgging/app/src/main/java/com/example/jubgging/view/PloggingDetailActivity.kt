package com.example.jubgging.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityPloggingDetailBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPolyline
import net.daum.mf.map.api.MapView

class PloggingDetailActivity : AppCompatActivity(), MapView.CurrentLocationEventListener,
    MapView.MapViewEventListener,
    MapView.POIItemEventListener {

    private lateinit var binding: ActivityPloggingDetailBinding
    private var mapView: MapView? = null  //카카오맵뷰
    private var mapViewContainer: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPloggingDetailBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //현재위치 버튼 눌렀을 때 트래킹모드 재시작
        binding.chmMyLocationBtn.setOnClickListener {
            mapView?.currentLocationTrackingMode =
                MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading

        }

    }

    override fun onResume() {
        super.onResume()

        //맵뷰 등록
        mapView = MapView(this)
        mapViewContainer = binding.ploggingKakaoMapView
        mapViewContainer?.addView(mapView)

        // 중심점 변경
        mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);

        // 줌 레벨 변경
        mapView!!.setZoomLevel(7, true);

        // 중심점 변경 + 줌 레벨 변경
        mapView!!.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(33.4111, 126.52),
            2,
            true
        );

        // 줌 인
        mapView!!.zoomIn(true);

        // 줌 아웃
        mapView!!.zoomOut(true);

        //mapView에 이벤트 등록
        mapView?.setMapViewEventListener(this)
        mapView?.setPOIItemEventListener(this)
        mapView?.setCurrentLocationEventListener(this)
    }


    //폴리라인 관련 변수
    private var polyline: MapPolyline? = null

    override fun onPause() {
        super.onPause()
        mapViewContainer?.removeAllViews()
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
        mapView?.currentLocationTrackingMode =
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

}