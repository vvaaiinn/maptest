package com.example.testmap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;

public class mapshow extends Activity
// implements OnGetGeoCoderResultListener
{

	MapView mMapView = null;
	BaiduMap mBaiduMap = null;
	GeoCoder mSearch = null;
	LinearLayout pp = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
		pp = (LinearLayout) findViewById(R.id.pp);

		// LatLng p = new LatLng(38.92, 121.62);
		// mMapView = new MapView(this,
		// new BaiduMapOptions().mapStatus(new MapStatus.Builder().target(
		// p).build()));
		// mBaiduMap = mMapView.getMap();
		// pp.addView(mMapView);

		// mMapView = (MapView) findViewById(R.id.bmapView);

		Double city = getIntent().getDoubleExtra("city", 38.92);
		Double add = getIntent().getDoubleExtra("add", 121.62);
		LatLng p = new LatLng(city, add);
		mMapView = new MapView(this,
				new BaiduMapOptions().mapStatus(new MapStatus.Builder().target(
						p).build()));
		mBaiduMap = mMapView.getMap();
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(p).icon(
				BitmapDescriptorFactory.fromResource(R.drawable.icon_marka)));
		pp.addView(mMapView);
		Toast.makeText(mapshow.this,
				city.toString() + add.toString() + "hahaha", Toast.LENGTH_LONG)
				.show();

		// ע������¼�
		// mSearch = GeoCoder.newInstance();
		// mSearch.setOnGetGeoCodeResultListener(this);
		// mSearch.geocode(new GeoCodeOption().city(city));

		// mBaiduMap = mMapView.getMap();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// ��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onPause();
		// mMapView.onDestroy();
	}

	// @Override
	// public void onGetGeoCodeResult(GeoCodeResult result) {
	// // TODO �Զ����ɵķ������
	// if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
	// Toast.makeText(mapshow.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG).show();
	// return;
	// }
	// String strInfo = String.format("γ�ȣ�%f ���ȣ�%f",
	// result.getLocation().latitude, result.getLocation().longitude);
	// LatLng p = new LatLng(result.getLocation().latitude,
	// result.getLocation().longitude);
	// mMapView = new MapView(this,
	// new BaiduMapOptions().mapStatus(new MapStatus.Builder().target(
	// p).build()));
	// mBaiduMap = mMapView.getMap();
	// pp.addView(mMapView);
	// }
	//
	// @Override
	// public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
	// // TODO �Զ����ɵķ������
	//
	// }

}
