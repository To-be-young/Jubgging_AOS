package com.example.jubgging.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityPloggingBinding
import com.example.jubgging.model.PloggingModel
import com.example.jubgging.network.data.request.PloggingRequest
import com.example.jubgging.viewmodel.CleanhouseViewModel
import net.daum.mf.map.api.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.timer

class PloggingActivity : AppCompatActivity(), MapView.CurrentLocationEventListener,
    MapView.MapViewEventListener,
    MapView.POIItemEventListener {

    //뷰 바인딩
    private lateinit var binding: ActivityPloggingBinding

    //뷰 모델
    private val viewModel: CleanhouseViewModel by viewModels()


    private val ACCESS_FINE_LOCATION = 1000     // Request Code
    private lateinit var mapView : MapView  //카카오맵뷰
    private lateinit var mapViewContainer: ViewGroup

    //onCurrentLocationUpdate()를 통해 받아온 위도, 경도 변수
    private var mCurrentLat: Double = 0.0
    private var mCurrentLng: Double = 0.0

    //map center Point Location
    private var mapCenterLat : Double = 0.0
    private var mapCenterLng : Double = 0.0

    //1초전의 사용자의 위치 변수
    private var beforeLat: Double = 0.0
    private var beforeLng: Double = 0.0

    //폴리라인 관련 변수
    private var polyline: MapPolyline? = null

    //플로깅을 시작했는지 안했는지 관련 변수
    private var plogging_start: Boolean = false

    //time
    var recentTime: Int = 0
    var startTime: Int = 0

    //distance
    var totalDistance: Double = 0.0

    //speed
    var speed: String = ""


    //timer
    private var time = 0
    private var timeTask: Timer? = null
    private var minute = 0
    private var second = 0

    //클린하우스 관련 변수
    var jsonArray: JSONArray? = null
    var marker: Array<MapPOIItem?>? = null

    //마커 관련 변수
    var address_hashMap = HashMap<Int, String>()
    var time_hashMap = HashMap<Int, String>()

    //plogging_post 데이터
    var ploggingList : ArrayList<PloggingModel> = ArrayList<PloggingModel>()
    var userActivityTime : String = ""
    var index : Int = 0
    var formattedTotalDistance : String = ""


    override fun onResume() {
        super.onResume()

        //맵뷰 등록
        mapView = MapView(this)
        mapViewContainer = binding.ploggingKakaoMapView
        mapViewContainer.addView(mapView)

        //트래킹 모드 시작
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading

        //현재위치 버튼 눌렀을 때 트래킹모드 재시작
        binding.chmMyLocationBtn.setOnClickListener {
            mapView.currentLocationTrackingMode =
                MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
        }

        //mapView에 이벤트 등록
        mapView.setMapViewEventListener(this)
        mapView.setPOIItemEventListener(this)
        mapView.setCurrentLocationEventListener(this)
        mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater))

        //Polyline 등록
        polyline = MapPolyline()
        polyline!!.tag = 1000

        //플로깅 시작
        binding.ploggingStartBt.setOnClickListener {
            plogging_start = true
            startTime = System.currentTimeMillis().toInt()

            startTimer()

            binding.ploggingStartBt.visibility = View.INVISIBLE
            binding.ploggingStopBt.visibility = View.VISIBLE
            binding.ploggingPauseBt.visibility = View.VISIBLE
        }

        //플로깅 종료
        binding.ploggingStopBt.setOnClickListener {
            binding.ploggingStopBt.visibility = View.INVISIBLE
            binding.ploggingPauseBt.visibility = View.INVISIBLE
            binding.ploggingStoreBt.visibility = View.VISIBLE

            stopTimer()

            plogging_start = false

            Log.d("lhj10", "STOP")
        }

        //저장 버튼
        binding.ploggingStoreBt.setOnClickListener {
            binding.ploggingStartBt.visibility = View.VISIBLE
            binding.ploggingStoreBt.visibility = View.INVISIBLE

            Log.d("formattedTotalDistance", "${formattedTotalDistance}")

            viewModel.sendPloggingResult(
                PloggingRequest(
                    "",
                    formattedTotalDistance.toDouble(),
                    userActivityTime,
                    speed,
                    pathway = ploggingList), ::showToast)

            Log.d("PloggingRequest", "${ploggingList}")
            binding.ploggingDistanceContextTv.text = "0Km"
            binding.ploggingPaceContextTv.text = "00`00"

            totalDistance = 0.0
            speed = "00`00"

            resetTimer()

            val intent = Intent(this, PloggingHistoryActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)

        }

        //플로깅 일시정지
        binding.ploggingPauseBt.setOnClickListener {
            plogging_start = false
            binding.ploggingStartBt.visibility = View.VISIBLE
            binding.ploggingStopBt.visibility = View.INVISIBLE
            binding.ploggingPauseBt.visibility = View.INVISIBLE

            stopTimer()

            Log.d("lhj10", "PAUSE")
        }

        viewModel.isSwitchOn.observe(this, Observer {
            if(it){
                val thread = NetworkThread()
                thread.start()
                thread.join()

                //클린하우스 마커 사라지면서 해시맵 초기화
                address_hashMap.clear()
                time_hashMap.clear()

                for (i in 0 until jsonArray!!.length()) {
                    val obj = jsonArray!!.getJSONObject(i)

                    //클린하우스 이름 추출
                    val location = obj.getString("location")
                    val marker_Lat = obj.getString("latitude").toDouble()
                    val marker_Lon = obj.getString("longitude").toDouble()
                    val cleanhouse_address = obj.getString("address")
                    val cleanhouse_time = obj.getString("time")

                    val tempmapPoint = MapPoint.mapPointWithGeoCoord(marker_Lat, marker_Lon)

                    Log.d("lhj123", "${location}")

                    val distanceKiloMeter: Double = distance(
                        marker_Lat,
                        marker_Lon,
                        mapCenterLat,
                        mapCenterLng,
                        "kilometer"
                    )

                    if (distanceKiloMeter < 2) {
                        marker!![i] = MapPOIItem()

                        marker!![i]!!.tag = i
                        marker!![i]!!.itemName = location
                        marker!![i]!!.mapPoint = tempmapPoint
                        marker!![i]!!.markerType = MapPOIItem.MarkerType.CustomImage
                        marker!![i]!!.customImageResourceId = R.drawable.plogging_cleanhouse_marker_imsi_img
//                        marker!![i]!!.isCustomImageAutoscale = false
//                        marker!![i]!!.setCustomImageAnchor(0.5f, 1.0f)
                        //해시맵에 태그, 값으로 매핑
                        address_hashMap.put(i, cleanhouse_address)
                        time_hashMap.put(i, cleanhouse_time)
                        mapView.addPOIItem(marker!![i])
                    }
                }
            }else{
                mapView.removeAllPOIItems()
            }
        })

        binding.chmSwitchBtn.setOnClickListener {
            viewModel.updateIsSwitchOn()
        }

    }


    override fun onPause() {
        super.onPause()
        viewModel.isSwitchOn.removeObservers(this)
        mapViewContainer.removeAllViews()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPloggingBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //툴바
//        val toolbar : androidx.appcompat.widget.Toolbar = binding.chmTb
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_plogging_back_main_bt)
//        supportActionBar?.setDisplayShowTitleEnabled(false)

        // 위치추적 버튼
        if (checkLocationService()) {
            // GPS가 켜져있을 경우
            permissionCheck()
        } else {
            // GPS가 꺼져있을 경우
            Toast.makeText(this, "GPS를 켜주세요", Toast.LENGTH_SHORT).show()
        }

    }


    inner class NetworkThread : Thread() {
        override fun run() {
            // API 정보를 가지고 있는 주소
            val site = "https://gist.githubusercontent.com/Yummy-sk/162dd1e4349ebf821f43db6c3c67f744/raw/ed25686c4f36e2b1474a8eeab2fa52837bdb5d93/jeju_clean_house"

            val url = URL(site)
            val conn = url.openConnection()
            val input = conn.getInputStream()
            val isr = InputStreamReader(input)
            // br: 라인 단위로 데이터를 읽어오기 위해서 만듦
            val br = BufferedReader(isr)

            // Json 문서는 일단 문자열로 데이터를 모두 읽어온 후, Json에 관련된 객체를 만들어서 데이터를 가져옴
            var str: String? = null
            val buf = StringBuffer()

            do {
                str = br.readLine()

                if (str != null) {
                    buf.append(str)
                }
            } while (str != null)

            // 전체가 객체로 묶여있기 때문에 객체형태로 가져옴

            // 11. 전달받은 데이터 확인.
            //                System.out.println(sb.toString());
            val data: String = buf.toString()

            //                System.out.println(data);
            jsonArray = JSONArray(data)
            marker = arrayOfNulls(jsonArray!!.length())

        }

        // 함수를 통해 데이터를 불러온다.
        fun JSON_Parse(obj: JSONObject, data: String): String {

            // 원하는 정보를 불러와 리턴받고 없는 정보는 캐치하여 "없습니다."로 리턴받는다.
            return try {

                obj.getString(data)

            } catch (e: Exception) {
                "없습니다."
            }
        }
    }

    //커스텀 말풍선 어댑터
    inner class CustomBalloonAdapter(inflater: LayoutInflater) : CalloutBalloonAdapter {
        val mCalloutBalloon: View = inflater.inflate(R.layout.ballon_layout, null)
        val name: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_name)
        val address: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_address)
        val time : TextView = mCalloutBalloon.findViewById(R.id.ball_tv_time)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // 마커 클릭 시 나오는 말풍선
            name.text = poiItem?.itemName   // 해당 마커의 정보 이용 가능
            address.text = address_hashMap.get(poiItem?.tag)
            time.text = time_hashMap.get(poiItem?.tag)

            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {

            return mCalloutBalloon
        }
    }

    // 위치 권한 확인
    private fun permissionCheck() {
        val preference = getPreferences(MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 없는 상태
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // 권한 거절 (다시 한 번 물어봄)
                val builder = AlertDialog.Builder(this)
                builder.setMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.")
                builder.setPositiveButton("확인") { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        ACCESS_FINE_LOCATION
                    )
                }
                builder.setNegativeButton("취소") { dialog, which ->

                }
                builder.show()
            } else {
                if (isFirstCheck) {
                    // 최초 권한 요청
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        ACCESS_FINE_LOCATION
                    )
                } else {
                    // 다시 묻지 않음 클릭 (앱 정보 화면으로 이동)
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("현재 위치를 확인하시려면 설정에서 위치 권한을 허용해주세요.")
                    builder.setPositiveButton("설정으로 이동") { dialog, which ->
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:$packageName")
                        )
                        startActivity(intent)
                    }
                    builder.setNegativeButton("취소") { dialog, which ->

                    }
                    builder.show()
                }
            }
        }
    }

    // 권한 요청 후 행동
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 요청 후 승인됨 (추적 시작)
                Toast.makeText(this, "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show()
            } else {
                // 권한 요청 후 거절됨 (다시 요청 or 토스트)
                Toast.makeText(this, "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show()
                permissionCheck()
            }
        }
    }

    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    // 위도, 경도 받아오기
    override fun onCurrentLocationUpdate(
        mapview: MapView?,
        mapPoint: MapPoint?,
        accuracyInMeters: Float
    ) {
        var mapPointGeo: MapPoint.GeoCoordinate = mapPoint!!.mapPointGeoCoord
        mCurrentLat = mapPointGeo.latitude //현재 위치 위도
        mCurrentLng = mapPointGeo.longitude //현재 위치 경도

        //플로깅 시작했을 때
        if (plogging_start) {
            polyline!!.lineColor = Color.argb(70, 0, 0, 255) // Polyline 컬러 지정.


            if (mCurrentLat != 0.0 && mCurrentLng != 0.0) {



                recentTime = System.currentTimeMillis().toInt()
                totalDistance += distance(mCurrentLat, mCurrentLng, beforeLat, beforeLng, "meter")

                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")
                val formatted = current.format(formatter)


                if(distance(mCurrentLat, mCurrentLng, beforeLat, beforeLng, "meter") < 2){
                    polyline!!.addPoint(MapPoint.mapPointWithGeoCoord(mCurrentLat, mCurrentLng))
                    mapView.addPolyline(polyline)
                    ploggingList.add(index, PloggingModel(mCurrentLat, mCurrentLng, formatted))

                    index++
                }

                Log.d("ploggingList", "${ploggingList}")

                Log.d("index", "$index")
            }
        }

        var passing_time = (recentTime - startTime) / 1000
        //속력을 00 : 00으로 포맷
        if(passing_time == 0){
            passing_time = 1
        }
        speed = String.format("%05.2f", totalDistance / passing_time)


        //속도 관련 변수 log
        Log.d("ex", "totalDistance : ${totalDistance}, passing_time : ${(passing_time / 1000)}, speed : ${speed}")

        Log.d("totalDistanceKm", "${totalDistance}")

        var totalDistanceKm = totalDistance / 1000

        Log.d("totalDistanceKm", "${totalDistanceKm}")
        formattedTotalDistance = String.format("%02.1f", totalDistanceKm)


        binding.ploggingDistanceContextTv.text = "${formattedTotalDistance}km"


        //speed를 .대신 `로 표시
        speed = speed.replace(".", "`")

        binding.ploggingPaceContextTv.text = speed
        beforeLat = mCurrentLat
        beforeLng = mCurrentLng

        Log.d("lhjlocation", "${mCurrentLng} ${mCurrentLng}")
    }

    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {

    }

    override fun onCurrentLocationUpdateFailed(p0: MapView?) {

    }

    override fun onCurrentLocationUpdateCancelled(p0: MapView) {

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

    //지도를 움직였을 때 트래킹모드 off
    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
        mapView?.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {

        mapCenterLat = mapView.mapCenterPoint.mapPointGeoCoord.latitude
        mapCenterLng = mapView.mapCenterPoint.mapPointGeoCoord.longitude

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


    // This function converts decimal degrees to radians
    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    // This function converts radians to decimal degrees
    private fun rad2deg(rad: Double): Double {
        return rad * 180 / Math.PI
    }

    private fun distance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double,
        unit: String
    ): Double {
        val theta = lon1 - lon2
        var dist =
            Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(
                deg2rad(lat1)
            ) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        if (unit == "kilometer") {
            dist = dist * 1.609344
        } else if (unit === "meter") {
            dist = dist * 1609.344
        }
        return dist
    }

    //타이머 시작
    private fun startTimer() {
        timeTask = timer(period = 10) {
            time++

            val sec = time / 100

            if (sec < 60) {
                second = sec
            } else {
                second = sec % 60
                minute = sec / 60
            }
            runOnUiThread {
                userActivityTime = String.format("%02d:%02d", minute, second)
                binding.ploggingTimeContextTv.text = userActivityTime
            }
        }
    }

    //타이머 정지
    private fun stopTimer() {
        timeTask?.cancel()
    }

    //타이머 리셋
    private fun resetTimer() {
        timeTask?.cancel()

        time = 0
        binding.ploggingTimeContextTv.text = "00:00"
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}