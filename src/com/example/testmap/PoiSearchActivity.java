package com.example.testmap;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;

public class PoiSearchActivity extends FragmentActivity implements
		OnGetPoiSearchResultListener, OnGetSuggestionResultListener {
	private BroadcastReceiver broadcastReceiver;
	public static String LOCATION_BCR = "location_bcr";
	Double lat = null;
	Double lon = null;// ��γ��
	LatLng p = null;// ����
	String add = null;// ��ַ

	ListView lv = null;// listview������
	Button listBtn = null;// detail�İ�ť
	View fr = null;
	private int type = 0;
	private PoiSearch mPoiSearch = null;
	private BaiduMap mBaiduMap = null;
	EditText editSearchKey = null;
	EditText editCity = null;

	/**
	 * �����ؼ������봰��
	 */
	private AutoCompleteTextView KWspot = null;// ��������
	private ArrayAdapter<String> spotAdapter = null;// �Զ���ȫ��adapter
	private int Index = 0;
	public ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
	public SimpleAdapter adapter = null; // ��ϸ��Ϣ��ʾ��adapter

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poisearch);
		// ��ʼ������ģ�飬ע�������¼�����
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);

		KWspot = (AutoCompleteTextView) findViewById(R.id.searchkey);
		String[] autoStrings = new String[] { "�Ƶ�", "����", "����", "��������", "����",
				"����", "ˮ����", "���", "����", "�ư�" };
		spotAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, autoStrings);
		KWspot.setAdapter(spotAdapter);
		KWspot.setThreshold(1);

		lv = (ListView) findViewById(R.id.listView);
		listBtn = (Button) findViewById(R.id.listbtn);
		fr = (View) findViewById(R.id.map);
		editCity = (EditText) findViewById(R.id.city);
		editSearchKey = (EditText) findViewById(R.id.searchkey);

		mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager()
				.findFragmentById(R.id.map))).getBaiduMap();

		registerBroadCastReceiver();
		MyApplication.getInstance().requestLocationInfo();

		listBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				if (adapter != null) {
					fr.setVisibility(View.GONE);
					lv.setAdapter(adapter);
					lv.setVisibility(View.VISIBLE);
				} else
					Toast.makeText(PoiSearchActivity.this, "��ѡ���ѯ",
							Toast.LENGTH_LONG).show();
			}
		});
		editCity.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO �Զ����ɵķ������
				if (hasFocus) {
					editCity.setText("");
				}
			}
		});
		editSearchKey.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO �Զ����ɵķ������
				if (hasFocus) {
					editSearchKey.setText("");
				}
			}
		});

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
		mPoiSearch.destroy();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	public void searchButtonProcess(View v) {

		lv.setVisibility(View.GONE);
		fr.setVisibility(View.VISIBLE);
		mPoiSearch.searchNearby((new PoiNearbySearchOption()).location(p)
				.keyword(editSearchKey.getText().toString()).radius(10000)
				.pageNum(Index));
		type = 0;
	}

	public void searchButtonProcess1(View v) {
		lv.setVisibility(View.GONE);
		fr.setVisibility(View.VISIBLE);

		mPoiSearch.searchInCity((new PoiCitySearchOption())
				.city(editCity.getText().toString())
				.keyword(editSearchKey.getText().toString()).pageNum(Index));
		type = 1;
	}

	public void goToNextPage(View v) {
		Index++;
		if (type == 0) {
			searchButtonProcess(null);
			adapter.notifyDataSetChanged();
		} else {
			searchButtonProcess1(null);
			adapter.notifyDataSetChanged();
		}
	}

	public void onGetPoiResult(PoiResult result) {
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(PoiSearchActivity.this, "δ�ҵ����", Toast.LENGTH_LONG)
					.show();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mBaiduMap.clear();
			arrayList.clear();
			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result);
			// System.out.println(result.getTotalPoiNum());
			// System.out
			// .println(overlay.getPoiResult().getAllPoi().get(5).address
			// .toString());
			for (int i = 0; i < 10; i++) {
				HashMap<String, String> temp = new HashMap<String, String>();
				temp.put("name", overlay.getPoiResult().getAllPoi().get(i).name
						.toString());
				temp.put("detail",
						overlay.getPoiResult().getAllPoi().get(i).address
								.toString());
				temp.put("phone",
						overlay.getPoiResult().getAllPoi().get(i).phoneNum
								.toString());
				arrayList.add(temp);
			}
			adapter = new SimpleAdapter(this, arrayList, R.layout.spotinfo,
					new String[] { "name", "detail", "phone" }, new int[] {
							R.id.name, R.id.detail, R.id.phone });

			mBaiduMap.addOverlay(new MarkerOptions().position(p)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.icon_marka)));
			overlay.addToMap();
			overlay.zoomToSpan();
			return;
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

			// ������ؼ����ڱ���û���ҵ����������������ҵ�ʱ�����ذ����ùؼ�����Ϣ�ĳ����б�
			String strInfo = "��";
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			strInfo += "�ҵ����";
			Toast.makeText(PoiSearchActivity.this, strInfo, Toast.LENGTH_LONG)
					.show();
		}
	}

	public void onGetPoiDetailResult(PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(PoiSearchActivity.this, "��Ǹ��δ�ҵ����",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(PoiSearchActivity.this,
					result.getName() + ": " + result.getAddress(),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onGetSuggestionResult(SuggestionResult res) {
		if (res == null || res.getAllSuggestions() == null) {
			return;
		}
		spotAdapter.clear();
		for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
			if (info.key != null)
				spotAdapter.add(info.key);
		}
		spotAdapter.notifyDataSetChanged();
	}

	private class MyPoiOverlay extends PoiOverlay {
		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
					.poiUid(poi.uid));
			return true;
		}
	}

	private void registerBroadCastReceiver() {
		broadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				lat = intent.getDoubleExtra("latitude", -1);
				lon = intent.getDoubleExtra("longitude", -1);
				add = intent.getStringExtra("add");
				// Toast.makeText(PoiSearchActivity.this, add.toString(),
				// Toast.LENGTH_LONG).show();
				p = new LatLng(lat, lon);
				mBaiduMap.clear();
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
