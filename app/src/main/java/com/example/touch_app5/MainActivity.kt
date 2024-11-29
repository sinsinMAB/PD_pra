package com.example.touch_app5;

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.touch_app5.R
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay



class MainActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private lateinit var locationOverlay: MyLocationNewOverlay
    private lateinit var compassOverlay: CompassOverlay


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // レイアウトのセット
        setContentView(R.layout.activity_main)

        // OSMDroidの設定
        Configuration.getInstance().setUserAgentValue(packageName)

        // MapViewの初期化
        map = findViewById(R.id.map)
        map.setMultiTouchControls(true)


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            setupLocationOverlay()
        }

        setupCompassOverlay()




        // 地図の初期設定（東京駅を表示）
        val mapController = map.controller
        mapController.setZoom(15.0)
        val startPoint = GeoPoint(35.6812, 139.7671) // 東京駅の座標
        mapController.setCenter(startPoint)



    }

    private fun setupLocationOverlay() {
        locationOverlay = MyLocationNewOverlay(map)
        locationOverlay.enableMyLocation()  // 現在地の取得
        locationOverlay.enableFollowLocation()  // 現在地を追跡
        map.overlays.add(locationOverlay)
    }

    private fun setupCompassOverlay() {
        compassOverlay = CompassOverlay(this, map)
        compassOverlay.enableCompass()  // コンパスの有効化
        map.overlays.add(compassOverlay)
    }

    // 権限のリクエスト結果を処理
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupLocationOverlay()
        }
    }


}
