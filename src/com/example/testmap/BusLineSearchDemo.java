package com.example.testmap;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.BusLineOverlay;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

/**
 * ��demo����չʾ��ν��й�����·�����������ʹ��RouteOverlay�ڵ�ͼ�ϻ��� ͬʱչʾ������·�߽ڵ㲢��������
 */
public class BusLineSearchDemo extends FragmentActivity implements
		OnGetPoiSearchResultListener, OnGetBusLineSearchResultListener,
		BaiduMap.OnMapClickListener {
	private Button mBtnPre = null;// ��һ���ڵ�
	private Button mBtnNext = null;// ��һ���ڵ�
	private int nodeIndex = -2;// �ڵ�����,������ڵ�ʱʹ��
	private BusLineResult route = null;// ����ݳ�/����·�����ݵı�����������ڵ�ʱʹ��
	private List<String> busLineIDList = null;
	private int busLineIndex = 0;
	// �������
	private PoiSearch mSearch = null; // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
	private BusLineSearch mBusLineSearch = null;
	private BaiduMap mBaiduMap = null;
	BusLineOverlay overlay;//����·�߻��ƶ���

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busline);
		CharSequence titleLable = "������·��ѯ����";
		setTitle(titleLable);
		mBtnPre = (Button) findViewById(R.id.pre);
		mBtnNext = (Button) findViewById(R.id.next);
		mBtnPre.setVisibility(View.INVISIBLE);
		mBtnNext.setVisibility(View.INVISIBLE);
		mBaiduMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.bmapView)).getBaiduMap();
		mBaiduMap.setOnMapClickListener(this);
		mSearch = PoiSearch.newInstance();
		mSearch.setOnGetPoiSearchResultListener(this);
		mBusLineSearch = BusLineSearch.newInstance();
		mBusLineSearch.setOnGetBusLineSearchResultListener(this);
		busLineIDList = new ArrayList<String>();
		overlay = new BusLineOverlay(mBaiduMap);
		mBaiduMap.setOnMarkerClickListener(overlay);
	}

	/**
	 * �������
	 * 
	 * @param v
	 */
	public void searchButtonProcess(View v) {
		busLineIDList.clear();
		busLineIndex = 0;
		mBtnPre.setVisibility(View.INVISIBLE);
		mBtnNext.setVisibility(View.INVISIBLE);
		EditText editCity = (EditText) findViewById(R.id.city);
		EditText editSearchKey = (EditText) findViewById(R.id.searchkey);
		// ����poi�������ӵõ�����poi���ҵ�������·���͵�poi����ʹ�ø�poi��uid���й�����������
		mSearch.searchInCity((new PoiCitySearchOption()).city(
				editCity.getText().toString()).keyword(
				editSearchKey.getText().toString()));
	}

	public void SearchNextBusline(View v) {
		if (busLineIndex >= busLineIDList.size()) {
			busLineIndex = 0;
		}
		if (busLineIndex >= 0 && busLineIndex < busLineIDList.size()
				&& busLineIDList.size() > 0) {
			mBusLineSearch.searchBusLine((new BusLineSearchOption()
					.city(((EditText) findViewById(R.id.city)).getText()
							.toString()).uid(busLineIDList.get(busLineIndex))));

			busLineIndex++;
		}

	}

	/**
	 * �ڵ����ʾ��
	 * 
	 * @param v
	 */
	public void nodeClick(View v) {

		if (nodeIndex < -1 || route == null
				|| nodeIndex >= route.getStations().size())
			return;
		TextView popupText = new TextView(this);
		popupText.setBackgroundResource(R.drawable.popup);
		popupText.setTextColor(0xff000000);
		// ��һ���ڵ�
		if (mBtnPre.equals(v) && nodeIndex > 0) {
			// ������
			nodeIndex--;
		}
		// ��һ���ڵ�
		if (mBtnNext.equals(v) && nodeIndex < (route.getStations().size() - 1)) {
			// ������
			nodeIndex++;
		}
		if(nodeIndex >= 0){
			// �ƶ���ָ������������
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(route
					.getStations().get(nodeIndex).getLocation()));
			// ��������
			popupText.setText(route.getStations().get(nodeIndex).getTitle());
			mBaiduMap.showInfoWindow(new InfoWindow(popupText, route.getStations()
					.get(nodeIndex).getLocation(), 0));
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mSearch.destroy();
		mBusLineSearch.destroy();
		super.onDestroy();
	}

	@Override
	public void onGetBusLineResult(BusLineResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(BusLineSearchDemo.this, "��Ǹ��δ�ҵ����",
					Toast.LENGTH_LONG).show();
			return;
		}
		mBaiduMap.clear();
		route = result;
		nodeIndex = -1;
		overlay.removeFromMap();
		overlay.setData(result);
		overlay.addToMap();
		overlay.zoomToSpan();
		mBtnPre.setVisibility(View.VISIBLE);
		mBtnNext.setVisibility(View.VISIBLE);
		Toast.makeText(BusLineSearchDemo.this, result.getBusLineName(),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onGetPoiResult(PoiResult result) {

		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(BusLineSearchDemo.this, "��Ǹ��δ�ҵ����",
					Toast.LENGTH_LONG).show();
			return;
		}
		// ��������poi���ҵ�����Ϊ������·��poi
		busLineIDList.clear();
		for (PoiInfo poi : result.getAllPoi()) {
			if (poi.type == PoiInfo.POITYPE.BUS_LINE
					|| poi.type == PoiInfo.POITYPE.SUBWAY_LINE) {
				busLineIDList.add(poi.uid);
			}
		}
		SearchNextBusline(null);
		route = null;
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {

	}

	@Override
	public void onMapClick(LatLng point) {
		mBaiduMap.hideInfoWindow();
	}

	@Override
	public boolean onMapPoiClick(MapPoi poi) {
		return false;
	}
}
