package com.example.testmap;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

public class RoutePlanActivity extends Activity implements
		BaiduMap.OnMapClickListener, OnGetRoutePlanResultListener {

	private BroadcastReceiver broadcastReceiver;
	public static String LOCATION_BCR = "location_bcr";
	String city = null;
	String add = null;
	Double lat = null;
	Double lon = null;
	LatLng p = null;
	Button btn = null;
	ListView lv = null;
	View fr = null;
	ArrayAdapter adapter = null;
	public List<String> arrayList = new ArrayList<String>();

	RouteLine route = null;
	OverlayManager routeOverlay = null;
	boolean useDefaultIcon = false;
	EditText editSt = null;
	EditText editEn = null;
	PlanNode start = null;
	PlanNode end = null;
	MapView mMapView = null; // ��ͼView
	private BaiduMap mBaiduMap = null;
	// �������
	RoutePlanSearch mSearch = null; // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.routeplan);

		registerBroadCastReceiver();
		MyApplication.getInstance().requestLocationInfo();

		// ��ʼ����ͼ
		mMapView = (MapView) findViewById(R.id.map);
		mBaiduMap = mMapView.getMap();
		btn = (Button) findViewById(R.id.listbtn);
		lv = (ListView) findViewById(R.id.listView);
		fr = (View) findViewById(R.id.map);
		editSt = (EditText) findViewById(R.id.start);
		editEn = (EditText) findViewById(R.id.end);
		mBaiduMap.setOnMapClickListener(this);
		// ��ʼ������ģ�飬ע���¼�����
		mSearch = RoutePlanSearch.newInstance();
		mSearch.setOnGetRoutePlanResultListener(this);

		editSt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO �Զ����ɵķ������
				if (hasFocus) {
					editSt.setText("");
				}
			}
		});
		editEn.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO �Զ����ɵķ������
				if (hasFocus) {
					editEn.setText("");
				}
			}
		});

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				if (btn.getText().toString().equals("��ϸ��Ϣ")) {
					if (adapter != null) {
						fr.setVisibility(View.GONE);
						lv.setAdapter(adapter);
						lv.setVisibility(View.VISIBLE);
						btn.setText("��ͼ��ʾ");
					} else {
						Toast.makeText(RoutePlanActivity.this, "��ѡ��һ�ַ�ʽ���в�ѯ",
								Toast.LENGTH_LONG).show();
					}
				} else {
					lv.setVisibility(View.GONE);
					fr.setVisibility(View.VISIBLE);
					btn.setText("��ϸ��Ϣ");
				}
			}
		});

	}

	public void SearchButtonProcess(View v) {
		// ��������ڵ��·������
		route = null;
		mBaiduMap.clear();

		// ����������ť��Ӧ

		start = PlanNode.withCityNameAndPlaceName("����", editSt.getText()
				.toString());
		if (editSt.getText().toString().equals("�ҵ�λ��")) {
			LatLng k = new LatLng(lat, lon);
			start = PlanNode.withLocation(k);
		}
		end = PlanNode.withCityNameAndPlaceName("����", editEn.getText()
				.toString());

		// ʵ��ʹ�����������յ���н�����ȷ���趨
		if (v.getId() == R.id.drive) {

			mSearch.drivingSearch((new DrivingRoutePlanOption()).from(start)
					.to(end));
		} else if (v.getId() == R.id.transit) {

			mSearch.transitSearch((new TransitRoutePlanOption()).from(start)
					.city("����").to(end));
		} else if (v.getId() == R.id.walk) {

			mSearch.walkingSearch((new WalkingRoutePlanOption()).from(start)
					.to(end));
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(RoutePlanActivity.this, "��Ǹ��δ�ҵ����",
					Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {

			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {

			route = result.getRouteLines().get(0);
			arrayList.clear();
			for (int i = 0; i < route.getAllStep().size(); i++) {
				arrayList.add(((WalkingRouteLine.WalkingStep) route
						.getAllStep().get(i)).getInstructions());
			}
			adapter = new ArrayAdapter<String>(this, R.layout.routeitem,
					arrayList);
			lv.setAdapter(adapter);
			WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			routeOverlay = overlay;
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}

	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {

		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(RoutePlanActivity.this, "��Ǹ��δ�ҵ����",
					Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {

			route = result.getRouteLines().get(0);
			arrayList.clear();
			for (int i = 0; i < route.getAllStep().size(); i++) {
				arrayList.add(((TransitRouteLine.TransitStep) route
						.getAllStep().get(i)).getInstructions());
			}
			adapter = new ArrayAdapter<String>(this, R.layout.routeitem,
					arrayList);
			lv.setAdapter(adapter);
			TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			routeOverlay = overlay;
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(RoutePlanActivity.this, "��Ǹ��δ�ҵ����",
					Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {

			route = result.getRouteLines().get(0);
			arrayList.clear();
			for (int i = 0; i < route.getAllStep().size(); i++) {
				arrayList.add(((DrivingRouteLine.DrivingStep) route
						.getAllStep().get(i)).getInstructions());
			}
			adapter = new ArrayAdapter<String>(this, R.layout.routeitem,
					arrayList);
			lv.setAdapter(adapter);
			DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
			routeOverlay = overlay;
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	// ����RouteOverly
	private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

		public MyDrivingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}

	private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

		public MyWalkingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}

	private class MyTransitRouteOverlay extends TransitRouteOverlay {

		public MyTransitRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}

	@Override
	public void onMapClick(LatLng point) {
		mBaiduMap.hideInfoWindow();
	}

	@Override
	public boolean onMapPoiClick(MapPoi poi) {
		return false;
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mSearch.destroy();
		mMapView.onDestroy();
		super.onDestroy();
	}

	private void registerBroadCastReceiver() {
		broadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				lat = intent.getDoubleExtra("latitude", -1);
				lon = intent.getDoubleExtra("longitude", -1);
				String add = intent.getStringExtra("add");
				p = new LatLng(lat, lon);
				mBaiduMap.clear();
				mBaiduMap
						.setMapStatus(MapStatusUpdateFactory
								.newMapStatus(new MapStatus.Builder().zoom(15)
										.build()));
				mBaiduMap.addOverlay(new MarkerOptions().position(p).icon(
						BitmapDescriptorFactory
								.fromResource(R.drawable.icon_marka)));
				mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(p));

			}
		};
		IntentFilter intentToReceiveFilter = new IntentFilter();
		intentToReceiveFilter.addAction(LOCATION_BCR);
		registerReceiver(broadcastReceiver, intentToReceiveFilter);
	}

}
